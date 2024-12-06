package com.aucloud.aupay.wallet.service;

import com.alibaba.fastjson.JSONObject;
import com.aucloud.aupay.constant.AupaySupplementFeeRecordState;
import com.aucloud.aupay.constant.CurrencyEnum;
import com.aucloud.aupay.constant.QueueConstant;
import com.aucloud.aupay.constant.WalletTransferTradeType;
import com.aucloud.aupay.dao.WalletDao;
import com.aucloud.aupay.entity.TransferRequest;
import com.aucloud.aupay.pojo.bo.AupayDigitalCurrencyWallet;
import com.aucloud.aupay.pojo.do_.AupayFeeWalletConfig;
import com.aucloud.aupay.pojo.do_.AupaySupplementFeeRecord;
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

/**
 * gas费钱包，提供gas费
 */
@Slf4j
@Service
public class GasWalletService {

    @Autowired
    private WalletDao walletDao;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private WalletService walletService;

    /**检查给定钱包id的gas费(实时链上)是否低于triggerAmount**/
    @RabbitListener(queuesToDeclare = {@Queue(value = QueueConstant.CHECK_TRANSFER_FEE, durable = "true", autoDelete = "false")})
    @Transactional
    public void checkWalletTransferFee(String message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) {
        int count = walletDao.checkPendingSupplementFeeRecord(message);
        if(count > 0) { //存在处理中补充手续费请求 防止重复补充
            return;
        }
        AupayDigitalCurrencyWallet wallet = walletDao.getWalletById(message);
        BigDecimal supplementAmount = ifSendGasAndGetSupplementAmount(wallet);
        if (supplementAmount.compareTo(BigDecimal.ZERO) > 0) {
            supplementTransferFee(wallet, supplementAmount, IdGenUtil.getUUID());
        }
    }

    public BigDecimal ifSendGasAndGetSupplementAmount(AupayDigitalCurrencyWallet wallet) {
        log.info("检查gas费用, walletId: {}", wallet.getWalletId());
        Integer currencyId = wallet.getCurrencyId();
        if(wallet.getCurrencyChain().equals(CurrencyEnum.CurrencyChainEnum.TRC.id)) {
            currencyId = CurrencyEnum.TRX.id;
        }
        if(wallet.getCurrencyChain().equals(CurrencyEnum.CurrencyChainEnum.ETH.id)) {
            currencyId = CurrencyEnum.ETH.id;
        }
        log.info("获取gas钱包配置, walletId: {}, currencyId:{},", wallet.getWalletId(), currencyId);
        AupayFeeWalletConfig feeWalletConfig = walletService.getFeeWalletConfig(currencyId, wallet.getCurrencyChain());//gas费钱包 配置
        if (feeWalletConfig != null && feeWalletConfig.getTriggerAmount()!=null) {
            BigDecimal gasBalance = walletService.getRealBalance(wallet, currencyId, wallet.getCurrencyChain());
            log.info("钱包:{} 当前链上gas费:{} ", wallet.getWalletId(), gasBalance);
            if(gasBalance==null || gasBalance.compareTo(feeWalletConfig.getTriggerAmount()) < 1) {
                //当前钱包的gas费低于 triggerAmount，需要补充手续费
                BigDecimal supplementAmount = feeWalletConfig.getSupplementAmount();
                log.info("小于设定费用:{},需要调用补充gas费，补充金额:{}", feeWalletConfig.getTriggerAmount(), supplementAmount);
                return supplementAmount;
            }
        }
        return BigDecimal.ZERO;
    }

    /**给钱包id补充gas手续费**/
    public TransferRequest supplementTransferFee(AupayDigitalCurrencyWallet toWallet, BigDecimal supplementAmount,String businessId) {
        log.info("supplement transfer fee: {}", toWallet.getWalletId());
        Integer currencyId = toWallet.getCurrencyId();
        Integer currencyChain = toWallet.getCurrencyChain();
        if(currencyChain.equals(CurrencyEnum.CurrencyChainEnum.TRC.id)) {
            currencyId = CurrencyEnum.TRX.id;
        }
        if(currencyChain.equals(CurrencyEnum.CurrencyChainEnum.ETH.id)) {
            currencyId = CurrencyEnum.ETH.id;
        }
        AupayDigitalCurrencyWallet feeWallet = walletService.getFeeWallet(currencyId, currencyChain);

        AupaySupplementFeeRecord aupaySupplementFeeRecord = new AupaySupplementFeeRecord();
        aupaySupplementFeeRecord.setId(businessId);
        aupaySupplementFeeRecord.setCurrencyId(toWallet.getCurrencyId());
        aupaySupplementFeeRecord.setCurrencyChain(toWallet.getCurrencyChain());
        aupaySupplementFeeRecord.setToWalletId(toWallet.getWalletId().toString());
        aupaySupplementFeeRecord.setFromWalletId(feeWallet.getWalletId().toString());
        aupaySupplementFeeRecord.setState(AupaySupplementFeeRecordState.PENDING);
        aupaySupplementFeeRecord.setAmount(supplementAmount);
        String transactionId = TransactionManageUtils.sign();
        aupaySupplementFeeRecord.setTransferRecordId(transactionId);
        walletDao.saveAupaySupplementFeeRecord(aupaySupplementFeeRecord);
        TransferRequest transferRequest = TransferRequest.getInstance(transactionId, feeWallet.getWalletId(), supplementAmount, toWallet.getAddress(), aupaySupplementFeeRecord.getId());
        transferRequest.setTradeType(WalletTransferTradeType.TRANSFER_FEE_SUPPLEMENT);
        transferRequest.setToWalletId(toWallet.getWalletId());
        rabbitTemplate.convertAndSend(QueueConstant.TRANSFER_DEAL, JSONObject.toJSONString(transferRequest));
        return transferRequest;
    }

    public void transferFeeSupplementComplete(AupayWalletTransferRecord aupayWalletTransferRecord) {
        String businessId = aupayWalletTransferRecord.getBusinessId();
        log.info("supplement transfer fee complete. businessId: {}", businessId);
        AupaySupplementFeeRecord aupaySupplementFeeRecord = new AupaySupplementFeeRecord();
        aupaySupplementFeeRecord.setId(businessId);
        aupaySupplementFeeRecord.setState(AupaySupplementFeeRecordState.SUCCESS);
        walletDao.updateAupaySupplementFeeRecord(aupaySupplementFeeRecord);
//        String toWalletId = aupayWalletTransferRecord.getToWalletId();
//        walletDao.addFeeBalance(toWalletId, aupayWalletTransferRecord.getAmount());
    }

    @Transactional
    public void transferFeeSupplementFail(String businessId) {
        log.info("supplement transfer fee fail: {}", businessId);
        AupaySupplementFeeRecord aupaySupplementFeeRecord = new AupaySupplementFeeRecord();
        aupaySupplementFeeRecord.setId(businessId);
        aupaySupplementFeeRecord.setState(AupaySupplementFeeRecordState.SUCCESS);
        walletDao.updateAupaySupplementFeeRecord(aupaySupplementFeeRecord);
    }

}
