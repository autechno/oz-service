package com.aucloud.aupay.wallet.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.aucloud.aupay.wallet.feign.FeignUserService;
import com.aucloud.aupay.wallet.orm.po.AcountChainWallet;
import com.aucloud.aupay.wallet.orm.po.WalletTransferRecord;
import com.aucloud.aupay.wallet.orm.service.AcountChainWalletService;
import com.aucloud.aupay.wallet.orm.service.WalletTransferRecordService;
import com.aucloud.constant.CurrencyEnum;
import com.aucloud.constant.QueueConstant;
import com.aucloud.constant.ResultCodeEnum;
import com.aucloud.constant.TradeType;
import com.aucloud.pojo.Result;
import com.aucloud.pojo.dto.AcountRechargeDTO;
import com.aucloud.pojo.dto.RechargeDTO;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class RechargeService {

    @Autowired
    private WalletTransferRecordService walletTransferRecordService;
    @Autowired
    private AcountChainWalletService acountChainWalletService;
    @Autowired
    private FeignUserService feignUserService;

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

            AcountChainWallet acountChainWallet = acountChainWalletService.getById(walletId);

            log.info("recharge getWalletById:{}", JSON.toJSONString(acountChainWallet));
            //此次根据currencyid判断不出来
            /*if(!walletById.getCurrencyId().equals(rechargeDTO.getCurrencyId())) { //不属于该钱包币种 暂不处理
                return;
            }*/
            BigDecimal amount = rechargeDTO.getAmount();
            if(amount.compareTo(currencyId.minRechargeAmount) < 0) { //未达到最小充币数量
                log.error("未达到最小充币数量");
                return;
            }

            String tradeNo = UUID.randomUUID().toString();
            WalletTransferRecord record = new WalletTransferRecord();
            record.setAmount(amount);
            record.setCurrencyChain(currencyChain.chainCurrencyId);
            record.setFromAddress(fromAddress);
            record.setToAddress(fromAddress);
            record.setCurrencyId(currencyId.id);
            record.setCreateTime(new Date());
            record.setTxHash(txId);
            record.setTradeType(TradeType.RECHARGE.getCode());
            record.setFee(BigDecimal.ZERO);
            record.setStatus(0);
            record.setTradeNo(tradeNo);
            record.setFinishTime(new Date());

            AcountRechargeDTO dto = new AcountRechargeDTO();
            dto.setTradeNo(tradeNo);
            dto.setAmount(amount);
            dto.setCurrencyEnum(currencyId);
            dto.setAccountId(acountChainWallet.getAcountId());
            dto.setAccountType(acountChainWallet.getAcountType());

            log.info("recharge state success. publishEvent RabbitMqMessage USER_RECHARGE_DEAL");
            Result<?> recharged = feignUserService.recharge(dto);
            if (recharged == null || recharged.getCode() != ResultCodeEnum.SUCCESS.getCode()) {
                record.setStatus(0);
            }
            walletTransferRecordService.save(record);
        } catch (Exception e) {
            log.error("", e);
            throw new RuntimeException(e);
        }
    }

}
