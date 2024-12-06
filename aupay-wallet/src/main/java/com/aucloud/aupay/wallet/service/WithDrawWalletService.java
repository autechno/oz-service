package com.aucloud.aupay.wallet.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aucloud.aupay.constant.QueueConstant;
import com.aucloud.aupay.constant.SupplementWithdrawAddressAssetsRecordState;
import com.aucloud.aupay.constant.WalletTransferTradeType;
import com.aucloud.aupay.dao.WalletDao;
import com.aucloud.aupay.entity.TransferRequest;
import com.aucloud.aupay.entity.WithdrawProcess;
import com.aucloud.aupay.feign.SysClient;
import com.aucloud.aupay.pojo.Result;
import com.aucloud.aupay.pojo.bo.AupayDigitalCurrencyWallet;
import com.aucloud.aupay.pojo.do_.AupaySupplementWithdrawWalletRecord;
import com.aucloud.aupay.pojo.do_.AupayWalletTransferRecord;
import com.aucloud.aupay.pojo.do_.AupayWithdrawConfig;
import com.aucloud.aupay.utils.IdGenUtil;
import com.aucloud.aupay.utils.ObjectUtils;
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

/**
 * 提币钱包，监控提币钱包金额，gas费同时监控，不够了从中转钱包转入，确保有钱提币
 */
@Slf4j
@Service
public class WithDrawWalletService {

    @Autowired
    private WalletDao walletDao;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private SysClient sysClient;
    @Autowired
    private WalletService walletService;

    @RabbitListener(queuesToDeclare = {@Queue(value = QueueConstant.CHECK_WITHDRAW_ADDRESS_ASSETS, durable = "true", autoDelete = "false")})
    @Transactional
    public void checkWithdrawAddressAssets(String message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel){
        log.info("check withdraw address assets message:{}",message);
        AupayDigitalCurrencyWallet wallet = walletDao.getWalletById(message);//提币钱包

        int count = walletDao.checkPendingSupplementWithdrawAddressAssetsRecord(message);
        if(count > 0) {
            //存在处理中的归集请求 防止重复补充
            return;
        }

        Result withdrawConfigResult = sysClient.getWithdrawConfig(wallet.getCurrencyId(), wallet.getCurrencyChain());
        log.info("withdrawConfigResult : {}", JSON.toJSONString(withdrawConfigResult));
        AupayWithdrawConfig aupayWithdrawConfig = ObjectUtils.convertObject(AupayWithdrawConfig.class,withdrawConfigResult.getData());
        BigDecimal triggerAmount = aupayWithdrawConfig.getTriggerAmount();
//        BigDecimal balance = walletById.getBalance();//改为链上实时余额
        BigDecimal realBalance = walletService.getRealBalance(wallet, wallet.getCurrencyId(), wallet.getCurrencyChain());
        if (realBalance == null || realBalance.compareTo(triggerAmount) < 0) {
            //钱包余额小于triggerAmount
//            rabbitTemplate.convertAndSend(QueueConstant.SUPPLEMENT_WITHDRAW_ADDRESS_ASSETS, message);//钱包id
            BigDecimal supplementAmount = aupayWithdrawConfig.getSupplementAmount();
            AupayDigitalCurrencyWallet transferWallet = walletService.getTransferWallet(wallet.getCurrencyId(),wallet.getCurrencyChain());//中转钱包
            //check transferWallet gas? balance?

            AupaySupplementWithdrawWalletRecord aupaySupplementWithdrawWalletRecord = new AupaySupplementWithdrawWalletRecord();
            aupaySupplementWithdrawWalletRecord.setId(IdGenUtil.getUUID());
            aupaySupplementWithdrawWalletRecord.setCreateTime(new Date());
            aupaySupplementWithdrawWalletRecord.setAmount(supplementAmount);
            aupaySupplementWithdrawWalletRecord.setCurrencyId(wallet.getCurrencyId());
            aupaySupplementWithdrawWalletRecord.setCurrencyChain(wallet.getCurrencyChain());
            aupaySupplementWithdrawWalletRecord.setToWalletId(wallet.getWalletId().toString());//被充值钱包 to
            aupaySupplementWithdrawWalletRecord.setFromWalletId(transferWallet.getWalletId().toString());// 充值钱包 from. 从中转钱包到提币钱包
            aupaySupplementWithdrawWalletRecord.setState(SupplementWithdrawAddressAssetsRecordState.PENDING);
            String transactionId = TransactionManageUtils.sign();
            aupaySupplementWithdrawWalletRecord.setTransferRecordId(transactionId);
            walletDao.saveAupaySupplementWithdrawWalletRecord(aupaySupplementWithdrawWalletRecord);
            TransferRequest transferRequest = TransferRequest.getInstance(transactionId, transferWallet.getWalletId(), supplementAmount, wallet.getAddress(), aupaySupplementWithdrawWalletRecord.getId());
            transferRequest.setTradeType(WalletTransferTradeType.WITHDRAW_SUPPLEMENT);
            transferRequest.setToWalletId(wallet.getWalletId());
            rabbitTemplate.convertAndSend(QueueConstant.TRANSFER_DEAL, JSONObject.toJSONString(transferRequest));
        }
    }

    /**
     * 提款地址资金补充到账 中转->提币
     */
    @Transactional
    public void withdrawSupplementTransferComplete(AupayWalletTransferRecord aupayWalletTransferRecord) {
        String businessId = aupayWalletTransferRecord.getBusinessId();
        log.info("提币钱包补充资金操作完成. businessId: {}", businessId);
        AupaySupplementWithdrawWalletRecord aupaySupplementWithdrawWalletRecord = new AupaySupplementWithdrawWalletRecord();
        aupaySupplementWithdrawWalletRecord.setId(businessId);
        aupaySupplementWithdrawWalletRecord.setState(SupplementWithdrawAddressAssetsRecordState.SUCCESS);
        walletDao.updateAupaySupplementWithdrawWalletRecord(aupaySupplementWithdrawWalletRecord);

//        String toWalletId = aupayWalletTransferRecord.getToWalletId();
//        walletDao.addFeeBalance(aupayWalletTransferRecord.getFromWalletId(), aupayWalletTransferRecord.getTransferFee());
//        walletDao.addBalance(toWalletId, aupayWalletTransferRecord.getAmount());
    }

    @Transactional
    public void withdrawSupplementTransferFail(String businessId) {
        log.info("提币钱包补充币失败 : {}", businessId);
        AupaySupplementWithdrawWalletRecord aupaySupplementWithdrawWalletRecord = new AupaySupplementWithdrawWalletRecord();
        aupaySupplementWithdrawWalletRecord.setId(businessId);
        aupaySupplementWithdrawWalletRecord.setState(SupplementWithdrawAddressAssetsRecordState.FAILED);
        walletDao.updateAupaySupplementWithdrawWalletRecord(aupaySupplementWithdrawWalletRecord);

    }

    @Transactional
    public void withdrawComplete(AupayWalletTransferRecord aupayWalletTransferRecord) {
//        walletDao.addFeeBalance(aupayWalletTransferRecord.getFromWalletId(), aupayWalletTransferRecord.getTransferFee().negate());
        String businessId = aupayWalletTransferRecord.getBusinessId();
        WithdrawProcess withdrawProcess = new WithdrawProcess();
        withdrawProcess.setBlockNumber(aupayWalletTransferRecord.getBlockNumber());
        withdrawProcess.setWithdrawId(businessId);
        rabbitTemplate.convertAndSend(QueueConstant.WITHDRAW_COMPLATE,JSONObject.toJSONString(withdrawProcess));
    }

    public void withDrawFail(String businessId) {
        WithdrawProcess withdrawProcess = new WithdrawProcess();
        withdrawProcess.setWithdrawId(businessId);
        rabbitTemplate.convertAndSend(QueueConstant.WITHDRAW_FAIL,JSONObject.toJSONString(withdrawProcess));
    }
}
