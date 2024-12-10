package com.aucloud.aupay.wallet.service;

import com.aucloud.aupay.wallet.feign.FeignEthContractService;
import com.aucloud.aupay.wallet.orm.constant.CollectEventType;
import com.aucloud.aupay.wallet.orm.po.ConfigWalletCollect;
import com.aucloud.aupay.wallet.orm.po.WalletCollectTaskRecord;
import com.aucloud.aupay.wallet.orm.po.WalletTransferRecord;
import com.aucloud.aupay.wallet.orm.service.ConfigWalletCollectService;
import com.aucloud.aupay.wallet.orm.service.WalletCollectTaskRecordService;
import com.aucloud.aupay.wallet.orm.service.WalletTransferRecordService;
import com.aucloud.constant.ResultCodeEnum;
import com.aucloud.aupay.wallet.orm.constant.TradeType;
import com.aucloud.constant.WalletTransferStatus;
import com.aucloud.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Slf4j
@Service
public class User2TransferService {

    @Autowired
    private ConfigWalletCollectService configWalletCollectService;
    @Autowired
    private GasService gasService;
    @Autowired
    private WalletTransferRecordService walletTransferRecordService;
    @Autowired
    private WalletCollectTaskRecordService walletCollectTaskRecordService;
    @Autowired
    private FeignEthContractService feignEthContractService;

    public void user2transfer(ConfigWalletCollect configWalletCollect, WalletCollectTaskRecord taskRecord) {
        log.info("user2transfer config Id: {}", configWalletCollect.getId());
        Integer currencyId = configWalletCollect.getCurrencyId();
        Integer currencyChain = configWalletCollect.getCurrencyChain();

        ConfigWalletCollect gasConfig = configWalletCollectService.getConfigWalletCollect(CollectEventType.GAS_TO_OPERATOR, currencyId, currencyChain);
        if (gasService.checkOperatorGas(gasConfig)) {
            //需要补充gas
            taskRecord.setStatus(WalletTransferStatus.SUSPEND);
            taskRecord.setFinishTime(new Date());
            taskRecord.setRemark("gas不足");
            walletCollectTaskRecordService.updateById(taskRecord);

            WalletCollectTaskRecord gastaskRecord = walletCollectTaskRecordService.generateTaskRecord(gasConfig.getId(), taskRecord.getId());
            if (!gasService.gas2operator(gasConfig, gastaskRecord)) {
                log.error("gas 补充中。");
                return;
            }
        }

        BigDecimal balanceLimit = configWalletCollect.getBalanceLimit();
        String tradeNo = taskRecord.getTradeNo();
        WalletTransferRecord transferRecord = new WalletTransferRecord();
        transferRecord.setCurrencyChain(currencyChain);

        transferRecord.setCurrencyId(currencyId);
        transferRecord.setStatus(WalletTransferStatus.PENDING);
        transferRecord.setTradeType(TradeType.USER_TO_TRANSFER);
        transferRecord.setTradeNo(tradeNo);
        walletTransferRecordService.save(transferRecord);

        String hash = "";
        Result<String> result = feignEthContractService.user2collect(currencyId, balanceLimit);
        if (result!=null&& result.getCode() == ResultCodeEnum.SUCCESS.getCode()) {
            hash = result.getData();
        }
        if (StringUtils.isNotBlank(hash)) {
            log.info("user2transfer hash: {}", hash);
            transferRecord.setTxHash(hash);
            walletTransferRecordService.updateById(transferRecord);

            taskRecord.setStatus(WalletTransferStatus.WAITING);
            taskRecord.setFinishTime(new Date());
            taskRecord.setRemark("success:hash "+hash);
            walletCollectTaskRecordService.updateById(taskRecord);
//            return "success:hash "+hash;
        } else {
            log.error("user2transfer error");
            transferRecord.setStatus(WalletTransferStatus.FAILED);
            transferRecord.setFinishTime(new Date());
            walletTransferRecordService.updateById(transferRecord);

            taskRecord.setStatus(WalletTransferStatus.FAILED);
            taskRecord.setFinishTime(new Date());
            taskRecord.setRemark("transfer failed");
            walletCollectTaskRecordService.updateById(taskRecord);
//            return "error:transfer failed";
        }
    }

    public void result(String tradeNo, Integer status) {
        walletCollectTaskRecordService.updateResult(tradeNo, status);
    }
}
