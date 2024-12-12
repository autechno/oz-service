package com.aucloud.aupay.wallet.service;

import com.aucloud.aupay.wallet.feign.FeignEthContractService;
import com.aucloud.aupay.wallet.orm.constant.CollectEventType;
import com.aucloud.aupay.wallet.orm.constant.TradeType;
import com.aucloud.aupay.wallet.orm.po.ConfigWalletCollect;
import com.aucloud.aupay.wallet.orm.po.WalletCollectTaskRecord;
import com.aucloud.aupay.wallet.orm.po.WalletTransferRecord;
import com.aucloud.aupay.wallet.orm.service.ConfigWalletAddressService;
import com.aucloud.aupay.wallet.orm.service.ConfigWalletCollectService;
import com.aucloud.aupay.wallet.orm.service.WalletCollectTaskRecordService;
import com.aucloud.aupay.wallet.orm.service.WalletTransferRecordService;
import com.aucloud.commons.constant.ResultCodeEnum;
import com.aucloud.commons.constant.WalletTransferStatus;
import com.aucloud.commons.constant.WalletType;
import com.aucloud.commons.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Slf4j
@Service
public class Transfer2StoreService {

    @Autowired
    private ChainTransferDealService chainTransferDealService;
    @Autowired
    private ConfigWalletAddressService configWalletAddressService;
    @Autowired
    private ConfigWalletCollectService configWalletCollectService;
    @Autowired
    private WalletTransferRecordService walletTransferRecordService;
    @Autowired
    private WalletCollectTaskRecordService walletCollectTaskRecordService;
    @Autowired
    private FeignEthContractService feignEthContractService;
    @Autowired
    private GasService gasService;

    public void transfer2store(ConfigWalletCollect configWalletCollect, WalletCollectTaskRecord taskRecord) {
        log.info("transfer2store config Id: {}", configWalletCollect.getId());
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
        String storeWallet = configWalletAddressService.getWalletAddress(WalletType.STORE.getCode(), currencyChain);
        if (StringUtils.isBlank(storeWallet)) {
            log.error("transfer2store error, storeWallet is empty");
            taskRecord.setStatus(WalletTransferStatus.FAILED);
            taskRecord.setFinishTime(new Date());
            taskRecord.setRemark("store wallet is empty");
            walletCollectTaskRecordService.updateById(taskRecord);
//            return "error:storeWallet is empty";
            return;
        }

        String transferWallet = configWalletAddressService.getWalletAddress(WalletType.TRANSFER.getCode(), currencyChain);
        if (StringUtils.isBlank(transferWallet)) {
            log.error("transfer2store error, transferWallet is empty");
            taskRecord.setStatus(WalletTransferStatus.FAILED);
            taskRecord.setFinishTime(new Date());
            taskRecord.setRemark("transfer wallet is empty");
            walletCollectTaskRecordService.updateById(taskRecord);
//            return "error:transferWallet is empty";
            return;
        }

        BigDecimal supplyAmount = configWalletCollect.getSupplyAmount();

        BigDecimal transferBalance = chainTransferDealService.getWalletBalance(transferWallet, currencyId);
        if (transferBalance.compareTo(balanceLimit) < 0) {
            log.info("中转钱包余额:{} < 设定限制:{} ,不需要归集", transferBalance, balanceLimit);
            taskRecord.setStatus(WalletTransferStatus.SUCCESS);
            taskRecord.setFinishTime(new Date());
            taskRecord.setRemark("小于限额，不需要归集.");
            walletCollectTaskRecordService.updateById(taskRecord);
//            return "success:no need to transfer";
            return;
        }
        supplyAmount = supplyAmount.min(transferBalance);

        String tradeNo = taskRecord.getTradeNo();
        WalletTransferRecord transferRecord = new WalletTransferRecord();
        transferRecord.setTradeNo(tradeNo);
        transferRecord.setCurrencyChain(currencyChain);
        transferRecord.setFromAddress(transferWallet);
        transferRecord.setToAddress(storeWallet);
        transferRecord.setAmount(supplyAmount);
//        transferRecord.setFee();
        transferRecord.setCurrencyId(currencyId);
        transferRecord.setStatus(WalletTransferStatus.PENDING);
        transferRecord.setTradeType(TradeType.TRANSFER_TO_STORE);
        walletTransferRecordService.save(transferRecord);

        String hash = "";
        Result<String> result = feignEthContractService.collect2store(storeWallet, currencyId, supplyAmount);
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
