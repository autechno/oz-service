package com.aucloud.aupay.wallet.service;

import com.alibaba.fastjson.JSON;
import com.aucloud.aupay.constant.QueueConstant;
import com.aucloud.aupay.constant.ReserveTransferWalletRecordState;
import com.aucloud.aupay.constant.WalletTransferTradeType;
import com.aucloud.aupay.dao.WalletDao;
import com.aucloud.aupay.entity.TransferRequest;
import com.aucloud.aupay.pojo.bo.AupayDigitalCurrencyWallet;
import com.aucloud.aupay.pojo.do_.AupayReserveTransferWalletRecord;
import com.aucloud.aupay.pojo.do_.AupayTransferWalletConfig;
import com.aucloud.aupay.pojo.do_.AupayWalletTransferRecord;
import com.aucloud.aupay.utils.TransactionManageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * 中转钱包，资金中转。
 * 监控币量大于设定值往储备钱包归集；
 * 提币钱包不够了 往提币钱包转；
 * 补充gas费 当需要时
 */
@Slf4j
@Service
public class TransferWalletService {

    @Autowired
    private WalletDao walletDao;
    @Autowired
    private RabbitTemplate rabbitTemplate;
//    @Autowired
//    private SysClient sysClient;
    @Autowired
    private WalletService walletService;

    /**中转账户 到 资金账户 归集**/
    public void checkTransferAddressAssets(AupayDigitalCurrencyWallet wallet) {
        log.info("资金归集 中转->储备 wallet id:{}", wallet.getWalletId());

        if (walletDao.checkPendingTransferAssets(wallet.getWalletId().toString()) > 0) {
            log.error("资金归集 中转->储备: 该钱包有正在归集中的记录。wallet id:{}", wallet.getWalletId());
            return;
        }

        AupayTransferWalletConfig aupayTransferWalletConfig = walletService.getTransferWalletConfig(wallet.getCurrencyId(), wallet.getCurrencyChain());
        if (aupayTransferWalletConfig != null
                && aupayTransferWalletConfig.getAutoReserveTriggerAmount() != null
                && aupayTransferWalletConfig.getAutoReserveTriggerAmount().compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal balance = walletService.getRealBalance(wallet, wallet.getCurrencyId(), wallet.getCurrencyChain());
            log.info("walletId:{} 链上余额:{}" , wallet.getWalletId(), balance);
            if (balance.compareTo(aupayTransferWalletConfig.getAutoReserveTriggerAmount()) >= 0) {
                log.info("大于设定值:{},需要归集.", aupayTransferWalletConfig.getAutoReserveTriggerAmount());
                BigDecimal reserveAmount = aupayTransferWalletConfig.getReserveAmount();
//                BigDecimal _amount = balance.subtract(aupayTransferWalletConfig.getAutoReserveTriggerAmount());
//                _amount = reserveAmount.compareTo(_amount) > 0 ? balance : reserveAmount;
                AupayDigitalCurrencyWallet reserveWallet = walletService.getReserveWallet(wallet.getCurrencyId(),wallet.getCurrencyChain());//to 储备钱包
                String transactionId = TransactionManageUtils.sign();
                AupayReserveTransferWalletRecord aupayReserveTransferWalletRecord = new AupayReserveTransferWalletRecord();
                aupayReserveTransferWalletRecord.setState(ReserveTransferWalletRecordState.PENDING);
                aupayReserveTransferWalletRecord.setTransferRecordId(transactionId);
                aupayReserveTransferWalletRecord.setAmount(reserveAmount);
                aupayReserveTransferWalletRecord.setCurrencyId(wallet.getCurrencyId());
                aupayReserveTransferWalletRecord.setCurrencyChain(wallet.getCurrencyChain());
                aupayReserveTransferWalletRecord.setFromWalletId(wallet.getWalletId().toString());
                aupayReserveTransferWalletRecord.setToWalletId(reserveWallet.getWalletId().toString());
                walletDao.saveAupayReserveTransferWalletRecord(aupayReserveTransferWalletRecord);
                TransferRequest transferRequest = TransferRequest.getInstance(transactionId, wallet.getWalletId(), reserveAmount, reserveWallet.getAddress(), aupayReserveTransferWalletRecord.getId());
                transferRequest.setTradeType(WalletTransferTradeType.TRANSFER_WALLET_RESERVE);
                transferRequest.setToWalletId(reserveWallet.getWalletId());
                rabbitTemplate.convertAndSend(QueueConstant.TRANSFER_DEAL, JSON.toJSONString(transferRequest));
            } else {
                log.info("小于getAutoReserveTriggerAmount，跳过此次归集");
            }
        } else {
            log.info("没有获取到正确的getAutoReserveTriggerAmount配置，跳过此钱包归集.");
        }
    }

    @Transactional
    public void transferWalletReserveComplete(AupayWalletTransferRecord aupayWalletTransferRecord) {
        String businessId = aupayWalletTransferRecord.getBusinessId();
        AupayReserveTransferWalletRecord aupayReserveTransferWalletRecord = new AupayReserveTransferWalletRecord();
        aupayReserveTransferWalletRecord.setId(businessId);
        aupayReserveTransferWalletRecord.setState(ReserveTransferWalletRecordState.SUCCESS);
        walletDao.updateAupayReserveTransferWalletRecord(aupayReserveTransferWalletRecord);

//        walletDao.addFeeBalance(aupayReserveTransferWalletRecord.getFromWalletId(), aupayWalletTransferRecord.getTransferFee().negate());
//        walletDao.addBalance(aupayWalletTransferRecord.getFromWalletId(), aupayWalletTransferRecord.getAmount().negate());
//        walletDao.addBalance(aupayWalletTransferRecord.getToWalletId(), aupayWalletTransferRecord.getAmount());
    }
    @Transactional
    public void transferWalletReserveFail(String businessId) {
        AupayReserveTransferWalletRecord aupayReserveTransferWalletRecord = new AupayReserveTransferWalletRecord();
        aupayReserveTransferWalletRecord.setId(businessId);
        aupayReserveTransferWalletRecord.setState(ReserveTransferWalletRecordState.FAILURE);
        walletDao.updateAupayReserveTransferWalletRecord(aupayReserveTransferWalletRecord);
    }
}
