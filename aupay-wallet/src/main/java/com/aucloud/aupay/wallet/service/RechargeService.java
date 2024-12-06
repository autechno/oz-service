package com.aucloud.aupay.wallet.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aucloud.aupay.constant.*;
import com.aucloud.aupay.pojo.bo.AupayDigitalCurrencyWallet;
import com.aucloud.aupay.pojo.do_.AupayUserAssetsCollectionConfig;
import com.aucloud.aupay.pojo.do_.AupayUserRechargeRecord;
import com.aucloud.aupay.pojo.dto.RechargeDTO;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Slf4j
@Service
public class RechargeService {

    @Autowired
    private WalletService walletService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 被动充值到账，或者说是 外部手动冲进来币的 toWallet
     * 充币到账钱包处理
     * 最小充币受理数量:
     * BTC 0.0001
     * ETH 0.0001
     * TRX 10
     * USDT 10
     */
    @RabbitListener(queuesToDeclare = {@Queue(value = QueueConstant.RECHARGE_DEAL, durable = "true", autoDelete = "false")})
    @Transactional
    public void recharge(String message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) {
        try {
            log.info("RabbitListener.RECHARGE_DEAL : {}", message);
            RechargeDTO rechargeDTO = JSONObject.parseObject(message, RechargeDTO.class);
            Integer blockNumber = rechargeDTO.getBlockNumber();
            String fromAddress = rechargeDTO.getFromAddress();
            String txId = rechargeDTO.getTxId();
            String walletId = rechargeDTO.getWalletId();//根据to address 获取到的
            CurrencyEnum currencyId = rechargeDTO.getCurrencyId();
            CurrencyEnum.CurrencyChainEnum currencyChain = rechargeDTO.getCurrencyChain();
            //此次为啥要锁？ 所以去掉
            /*String requestId = LockUtils.genRequestId();
            String lockName = LockName.WALLET_BALANCE_CHANGE + walletId;
            Boolean lock = LockUtils.tryGetLock(lockName, requestId, 10L, 5);
            if(!lock) {
                logger.error("recharge lock fail");
                rabbitTemplate.convertAndSend(QueueConstant.RECHARGE_DEAL , message);
                return;
            }
            logger.info("publishEvent LockUtils.LockInfo.");
            applicationEventPublisher.publishEvent(new LockUtils.LockInfo(lockName,requestId));*/
            AupayDigitalCurrencyWallet wallet = walletService.getWalletById(walletId);
            log.info("recharge getWalletById:{}", JSON.toJSONString(wallet));
            //此次根据currencyid判断不出来
            /*if(!walletById.getCurrencyId().equals(rechargeDTO.getCurrencyId())) { //不属于该钱包币种 暂不处理
                return;
            }*/
            BigDecimal amount = rechargeDTO.getAmount();
            if(amount.compareTo(currencyId.minRechargeAmount) < 0) { //未达到最小充币数量
                log.error("未达到最小充币数量");
                return;
            }
            log.info("walletById.getUseWay(): {}", wallet.getUseWay());
//            if(WalletUseWay.TRANSFER_FEE.equals(wallet.getUseWay()) || WalletUseWay.USER.equals(wallet.getUseWay())) {
//                //用户充值、或 中转钱包被充值 或gas费用钱包被充值
//                walletService.addBalance(walletId, amount);//更新余额
//            }
            /*AupayUserWalletRelation aupayUserWalletRelation = walletDao.getWalletUserRelation(walletId);
            log.info("getWalletUserRelation : {}", JSON.toJSONString(aupayUserWalletRelation));
            if(aupayUserWalletRelation == null) { //不是使用中用户钱包 暂不处理
                log.error("不是使用中用户钱包 暂不处理,");
                return;
            }*/
            String userId = wallet.getUserId();//用户钱包
            AupayUserRechargeRecord aupayUserRechargeRecord = new AupayUserRechargeRecord();
            aupayUserRechargeRecord.setUserId(userId);
            aupayUserRechargeRecord.setAmount(amount);
            aupayUserRechargeRecord.setCreateTime(new Date());
            aupayUserRechargeRecord.setFromAddress(fromAddress);
            aupayUserRechargeRecord.setToAddress(wallet.getAddress());
            aupayUserRechargeRecord.setBlockNum(blockNumber);
            aupayUserRechargeRecord.setCurrencyId(wallet.getCurrencyId());
            aupayUserRechargeRecord.setCurrencyChain(wallet.getCurrencyChain());
            aupayUserRechargeRecord.setToWalletId(walletId);
            aupayUserRechargeRecord.setChainTxId(txId);
            if(rechargeDTO.getState()!=null && rechargeDTO.getState().equals(TxStatus.PENDING)) {
                aupayUserRechargeRecord.setState(UserRechargeRecordState.PENDING);
            } else {
                aupayUserRechargeRecord.setState(UserRechargeRecordState.SUCCESS);
                log.info("recharge state success. publishEvent RabbitMqMessage USER_RECHARGE_DEAL");
                rabbitTemplate.convertAndSend(QueueConstant.USER_RECHARGE_DEAL,JSONObject.toJSONString(aupayUserRechargeRecord));
            }
            /*log.info("recharge state success. publishEvent RabbitMqMessage CHECK_USER_ASSETS");
            //判断当前钱包是否有归集中的业务处理，如果有正在归集中的，就不需要发送归集消息了
            String requestId = LockUtils.genRequestId();
            String lockName = LockName.WALLET_BALANCE_CHANGE + walletId;
            Boolean lock = LockUtils.tryGetLock(lockName, requestId, 10L, 5);
            if(!lock) {
                //未锁定成功，意味着有正在进行中的归集任务，解锁应当在发起归集转账操作之后 或者 判断不需要归集之后 LockUtils.releaseLock(lockName, requestId);
                log.info("钱包存在正在进行中的归集操作，此次跳过");
            }*/
            AupayUserAssetsCollectionConfig aupayUserAssetsCollectionConfig = walletService.getUserAssetsCollectionConfig();
            if (aupayUserAssetsCollectionConfig != null) {
                BigDecimal anytimeAutoTriggerAmount = aupayUserAssetsCollectionConfig.getAnytimeAutoTriggerAmount();
                if (anytimeAutoTriggerAmount!=null&&anytimeAutoTriggerAmount.compareTo(BigDecimal.ZERO)>0) {
                    String msg = walletId + "#" + anytimeAutoTriggerAmount;
                    rabbitTemplate.convertAndSend(QueueConstant.CHECK_USER_ASSETS, msg);
                }
            }
        } catch (Exception e) {
            log.error("", e);
            throw new RuntimeException(e);
        }
    }

}
