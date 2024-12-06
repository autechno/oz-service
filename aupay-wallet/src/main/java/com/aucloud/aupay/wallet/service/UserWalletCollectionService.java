package com.aucloud.aupay.wallet.service;

import com.alibaba.fastjson.JSONObject;
import com.aucloud.aupay.constant.CollectUserAssetsRecordState;
import com.aucloud.aupay.constant.CurrencyEnum;
import com.aucloud.aupay.constant.QueueConstant;
import com.aucloud.aupay.constant.WalletTransferTradeType;
import com.aucloud.aupay.dao.WalletDao;
import com.aucloud.aupay.entity.TransferRequest;
import com.aucloud.aupay.pojo.bo.AupayDigitalCurrencyWallet;
import com.aucloud.aupay.pojo.do_.AupayCollectionUserAssetsRecord;
import com.aucloud.aupay.pojo.do_.AupayUserAssetsCollectionConfig;
import com.aucloud.aupay.pojo.do_.AupayWalletTransferRecord;
import com.aucloud.aupay.utils.IdGenUtil;
import com.aucloud.aupay.utils.TransactionManageUtils;
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
import java.util.List;

/**
 * 用户钱包 主要是归集服务
 */
@Slf4j
@Service
@Transactional
public class UserWalletCollectionService {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private WalletService walletService;
    @Autowired
    private WalletDao walletDao;

    public void userAssetsCollect() {
        AupayUserAssetsCollectionConfig aupayUserAssetsCollectionConfig = walletService.getUserAssetsCollectionConfig();
        if (aupayUserAssetsCollectionConfig != null) {
            BigDecimal amountLimit = aupayUserAssetsCollectionConfig.getManualAmountLimit();
            for(CurrencyEnum currencyEnum : CurrencyEnum.values()) {
                String[] chains = currencyEnum.supportChain.split(",");
                for(String chain : chains) {
                    int _chain = Integer.parseInt(chain);
                    userAssetsCollect(currencyEnum.id, _chain, amountLimit);
                }
            }
        }
    }

    public void userAssetsCollect(BigDecimal limitAmount) {
        for(CurrencyEnum currencyEnum : CurrencyEnum.values()) {
            String[] chains = currencyEnum.supportChain.split(",");
            for (String chain : chains) {
                int _chain = Integer.parseInt(chain);
                userAssetsCollect(currencyEnum.id, _chain, limitAmount);
            }
        }
    }

    private void userAssetsCollect(Integer currencyId, Integer chain, BigDecimal limitAmount) {
        List<AupayDigitalCurrencyWallet> list = walletDao.getUserWalletByCurrencyChain(currencyId,chain);
        for(AupayDigitalCurrencyWallet aupayDigitalCurrencyWallet : list) {
            String msg = aupayDigitalCurrencyWallet.getWalletId() + "#" + limitAmount.toString();
            rabbitTemplate.convertAndSend(QueueConstant.CHECK_USER_ASSETS, msg);
        }
    }

//    public boolean checkAndStartCollectionMassage(String walletId, BigDecimal triggerAmount) {
//        //判断当前钱包是否有归集中的业务处理，如果有正在归集中的，就不需要发送归集消息了
//        String requestId = LockUtils.genRequestId();
//        String lockName = LockName.USER_WALLET_COLLECT_LOCK + walletId;
//        Boolean lock = LockUtils.tryGetLock(lockName, requestId, 10L, 5);
//        if(!lock) {
//            //未锁定成功，意味着有正在进行中的归集任务，解锁应当在发起归集转账操作之后 或者 判断不需要归集之后 LockUtils.releaseLock(lockName, requestId);
//            log.info("钱包存在正在进行中的归集操作，此次跳过");
//            return false;
//        }
//
//        String msg = walletId + "#" + triggerAmount.toString();
//        rabbitTemplate.convertAndSend(QueueConstant.CHECK_USER_ASSETS, msg);
//        return true;
//    }

    private boolean checkPendingUserAssets(String walletId) {
        int i = walletDao.checkPendingCollectUserAssets(walletId);
        return i > 0;
    }
    /**资金归集**/
    @RabbitListener(queuesToDeclare = {@Queue(value = QueueConstant.CHECK_USER_ASSETS, durable = "true", autoDelete = "false")})
    @Transactional
    public void checkUserAssets(String message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel){
        log.info("RabbitListener.CHECK_USER_ASSETS. message : {}", message);
        try {
            String[] split = message.split("#");
            String walletId = split[0];

            if(checkPendingUserAssets(walletId)){
                log.error("当前钱包存在pending中的任务,walletId={}",walletId);
                return;
            }
            BigDecimal limitAmount = split.length>1 ? new BigDecimal(split[1]) : BigDecimal.valueOf(Long.MAX_VALUE);
            AupayDigitalCurrencyWallet wallet = walletDao.getWalletById(walletId);

            log.info("查询账户链上余额.");
            BigDecimal balance = walletService.getRealBalance(wallet, wallet.getCurrencyId(), wallet.getCurrencyChain());//换成实时余额
            if (balance == null || balance.compareTo(limitAmount) < 0) {
                log.error("(实时)balanceOf 未空 或 未超过设定值，不执行归集. walletId: {} ; balance: {}", walletId, balance);
                return;
            }
            log.info("(实时)balanceOf 超过设定值，执行自动归集，发冲gas费用消息到队列. message: {} ; balance: {}", walletId, balance);
            AupayDigitalCurrencyWallet transferWallet = walletService.getTransferWallet(wallet.getCurrencyId(), wallet.getCurrencyChain());//转到归集钱包 中转钱包
            AupayCollectionUserAssetsRecord aupayCollectionUserAssetsRecord = new AupayCollectionUserAssetsRecord();
            String businessId = IdGenUtil.getUUID();
            aupayCollectionUserAssetsRecord.setId(businessId);
            aupayCollectionUserAssetsRecord.setCreateTime(new Date());
            aupayCollectionUserAssetsRecord.setCurrencyId(wallet.getCurrencyId());
            aupayCollectionUserAssetsRecord.setCurrencyChain(wallet.getCurrencyChain());
            aupayCollectionUserAssetsRecord.setAmount(balance);
            aupayCollectionUserAssetsRecord.setFromWalletId(wallet.getWalletId().toString());
            aupayCollectionUserAssetsRecord.setToWalletId(transferWallet.getWalletId().toString());
            aupayCollectionUserAssetsRecord.setState(CollectUserAssetsRecordState.PENDING);
            walletDao.saveAupayCollectionUserAssetsRecord(aupayCollectionUserAssetsRecord);
            //判断 gas费 是否需要补充 ，如不需要补充 则 直接归集。如需充值 则冲 并且后续操作 在处理完冲gas事务后 执行。
//            if (!gasFeeCheckAndSupply(wallet,businessId)) {
//                log.info("钱包:{} gas费用充足，不需要冲gas费用，直接归集。", walletId);
                userAssetsCollectFeeTransferComplete(businessId);
//            }
        } catch (Exception e) {
            log.error("RabbitListener.CHECK_USER_ASSETS error. walletId#limit: {} ", message, e);
            throw new RuntimeException(e);
        } finally {
            //释放lock
        }
    }

//    /**监听归集转账失败消息**/
//    @RabbitListener(queues = QueueConstant.CHECK_USER_ASSETS_TRANSFER_SYNC_STATE)
//    public void userAssetsSyncFail(String message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) {
//        log.info("资金归集--调用链转账失败；businessId:{}", message);
//        AupayCollectionUserAssetsRecord aupayCollectionUserAssetsRecord = new AupayCollectionUserAssetsRecord();
//        aupayCollectionUserAssetsRecord.setId(message);
//        aupayCollectionUserAssetsRecord.setState(CollectUserAssetsRecordState.FAILURE);
//        walletDao.updateAupayCollectionUserAssetsRecord(aupayCollectionUserAssetsRecord);
//    }

//    /**判断gas费用是否够用，不够则转gas费**/
//    private boolean gasFeeCheckAndSupply(AupayDigitalCurrencyWallet wallet, String businessId) {
//        Integer feeWalletCurrencyId = wallet.getCurrencyId();
//        if(wallet.getCurrencyChain().equals(CurrencyEnum.CurrencyChainEnum.TRC20.id)) {
//            feeWalletCurrencyId = CurrencyEnum.TRX.id;
//        }
//        if(wallet.getCurrencyChain().equals(CurrencyEnum.CurrencyChainEnum.ERC20.id)) {
//            feeWalletCurrencyId = CurrencyEnum.ETH.id;
//        }
//        Result feeWalletConfieegResult = sysClient.getFeeWalletConfig(feeWalletCurrencyId, CurrencyEnum.CurrencyChainEnum.DEFAULT.id);
//        AupayFeeWalletConfig feeWalletConfig = ObjectUtils.convertObject(AupayFeeWalletConfig.class,feeWalletConfieegResult.getData());
//        BigDecimal triggerAmount = feeWalletConfig.getTriggerAmount();//????
//        BigDecimal realBalance = walletService.getRealBalance(wallet, feeWalletCurrencyId, wallet.getCurrencyChain());
//        log.info("钱包:{} 当前链上gas费:{} ", wallet.getWalletId(), realBalance);
//        if(realBalance == null || realBalance.compareTo(triggerAmount) < 0) {
//            log.info("小于设定费用:{},需要调用转gas费", triggerAmount);
//            //首先手续费补充
//            AupayDigitalCurrencyWallet feeWallet = walletDao.getFeeWallet(feeWalletCurrencyId, CurrencyEnum.CurrencyChainEnum.DEFAULT.id);
//
//            String transactionId = TransactionManageUtils.sign();
//            TransferRequest transferRequest = TransferRequest.getInstance(transactionId, feeWallet.getWalletId(), feeWalletConfig.getSupplementAmount(), wallet.getAddress(), businessId);
//            transferRequest.setTradeType(WalletTransferTradeType.USER_ASSETS_COLLECTION_SUPPLEMENT_FEE);//调用 资金归集--冲gas费交易
//            transferRequest.setToWalletId(wallet.getWalletId());
//            pushTransferRequest(transferRequest);
//            return true;
//        }
//        return false;
//    }

    /**资金归集--冲gas费交易 到账更新记录；调用归集**/
    @Transactional
    public void userAssetsCollectFeeTransferComplete(String businessId) {
        log.info("资金归集--冲gas费 成功 更新记录状态；businessId:{}", businessId);
        AupayCollectionUserAssetsRecord aupayCollectionUserAssetsRecord = walletDao.getAupayCollectionUserAssetsRecord(businessId);
        String transactionId = TransactionManageUtils.sign();
        aupayCollectionUserAssetsRecord.setTransferRecordId(transactionId);

        Integer status = CollectUserAssetsRecordState.PROCESS;
        log.debug("资金归集--冲gas费 到账 发起资金归集消息");
        try {
            AupayDigitalCurrencyWallet fromWallet = walletDao.getWalletById(aupayCollectionUserAssetsRecord.getFromWalletId());//用户钱包 被归集钱包
            AupayDigitalCurrencyWallet transferWallet = walletService.getTransferWallet(fromWallet.getCurrencyId(), fromWallet.getCurrencyChain());//归集到 中转钱包
            BigDecimal amount = aupayCollectionUserAssetsRecord.getAmount();
            TransferRequest transferRequest = TransferRequest.getInstance(transactionId, fromWallet.getWalletId(), amount, transferWallet.getAddress(), businessId);
            transferRequest.setTradeType(WalletTransferTradeType.USER_ASSETS_COLLECTION);//调用 资金归集交易
            transferRequest.setToWalletId(transferWallet.getWalletId());
            pushTransferRequest(transferRequest);
        } catch (Exception e) {
            status = CollectUserAssetsRecordState.FAILURE;
            log.error("发送资金归集消息失败", e);
        }
        aupayCollectionUserAssetsRecord.setState(status);
        walletDao.updateAupayCollectionUserAssetsRecord(aupayCollectionUserAssetsRecord);
    }
    @Transactional
    public void userAssetsCollectFeeTransferFail(String businessId) {
        log.info("资金归集--冲gas费 失败. 更新记录状态；businessId:{}", businessId);
        AupayCollectionUserAssetsRecord aupayCollectionUserAssetsRecord = walletDao.getAupayCollectionUserAssetsRecord(businessId);
        aupayCollectionUserAssetsRecord.setState(CollectUserAssetsRecordState.FAILURE);
        walletDao.updateAupayCollectionUserAssetsRecord(aupayCollectionUserAssetsRecord);
    }

    /**发送转账请求**/
    private void pushTransferRequest(TransferRequest transferRequest) {
        log.info("push transfer request: {}", transferRequest);
        rabbitTemplate.convertAndSend(QueueConstant.TRANSFER_DEAL, JSONObject.toJSONString(transferRequest));
    }

    /**
     * 用户资产转账完毕
     * @param aupayWalletTransferRecord 转账记录
     */
    @Transactional
    public void userAssetsCollectTransferComplete(AupayWalletTransferRecord aupayWalletTransferRecord) {
        String businessId = aupayWalletTransferRecord.getBusinessId();
        log.info("user assets collect complate. businessId: {}", businessId);
        AupayCollectionUserAssetsRecord aupayCollectionUserAssetsRecord = walletDao.getAupayCollectionUserAssetsRecord(businessId);

//        String toWalletId = aupayWalletTransferRecord.getToWalletId();
//        walletDao.addBalance(toWalletId, aupayWalletTransferRecord.getAmount());//增加 中转钱包 余额

        aupayCollectionUserAssetsRecord.setState(CollectUserAssetsRecordState.SUCCESS);
        walletDao.updateAupayCollectionUserAssetsRecord(aupayCollectionUserAssetsRecord);
        log.error("用户资金归集完成");
    }

    @Transactional
    public void userAssetsCollectTransferFail(String businessId) {
        log.info("user assets collect fail: {}", businessId);
        AupayCollectionUserAssetsRecord aupayCollectionUserAssetsRecord = walletDao.getAupayCollectionUserAssetsRecord(businessId);
        if (aupayCollectionUserAssetsRecord == null) {
            return;
        }
        aupayCollectionUserAssetsRecord.setState(CollectUserAssetsRecordState.FAILURE);
        walletDao.updateAupayCollectionUserAssetsRecord(aupayCollectionUserAssetsRecord);
    }
}
