package com.aucloud.aupay.wallet.service;

import com.alibaba.fastjson2.JSON;
import com.aucloud.aupay.wallet.feign.FeignEthService;
import com.aucloud.aupay.wallet.orm.constant.TradeType;
import com.aucloud.aupay.wallet.orm.po.ConfigWalletAddress;
import com.aucloud.aupay.wallet.orm.po.ConfigWalletCollect;
import com.aucloud.aupay.wallet.orm.po.WalletCollectTaskRecord;
import com.aucloud.aupay.wallet.orm.po.WalletTransferRecord;
import com.aucloud.aupay.wallet.orm.service.ConfigWalletAddressService;
import com.aucloud.aupay.wallet.orm.service.WalletCollectTaskRecordService;
import com.aucloud.aupay.wallet.orm.service.WalletTransferRecordService;
import com.aucloud.constant.*;
import com.aucloud.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Slf4j
@Service
public class GasService {

    @Autowired
    private ChainTransferDealService chainTransferDealService;
    @Autowired
    private FeignEthService feignEthService;
    @Autowired
    private ConfigWalletAddressService configWalletAddressService;
    @Autowired
    private WalletTransferRecordService walletTransferRecordService;
    @Autowired
    private WalletCollectTaskRecordService walletCollectTaskRecordService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public boolean checkOperatorGas(ConfigWalletCollect gasConfig) {
        ConfigWalletAddress operatorConfig = configWalletAddressService.lambdaQuery()
                .eq(ConfigWalletAddress::getWalletType, WalletType.OPERATOR.getCode())
                .eq(ConfigWalletAddress::getCurrencyChain, gasConfig.getCurrencyChain())
                .oneOpt().orElseThrow();
        String operatorWallet = operatorConfig.getWalletAddress();
        if (StringUtils.isBlank(operatorWallet)) {
            log.error("gas2operator error, operatorWallet is empty");
            return false;
        }
        BigDecimal balanceLimit = gasConfig.getBalanceLimit();
        Result<BigDecimal> ethBalance = feignEthService.getBalance(operatorWallet, CurrencyEnum.ETH.id);
        if (ethBalance != null && ethBalance.getCode() == ResultCodeEnum.SUCCESS.getCode()) {
            BigDecimal eth = ethBalance.getData();
            if (eth.compareTo(balanceLimit) < 0) {
                log.info("operator wallet gas不足。需要补充gas费");
                return true;
            }
        } else {
            log.error("gas2operator error, ethBalance is empty");
        }
        return false;
    }

    public boolean gas2operator(ConfigWalletCollect configWalletCollect, WalletCollectTaskRecord taskRecord) {
        Integer currencyChain = configWalletCollect.getCurrencyChain();
        ConfigWalletAddress operatorConfig = configWalletAddressService.lambdaQuery()
                .eq(ConfigWalletAddress::getWalletType, WalletType.OPERATOR.getCode())
                .eq(ConfigWalletAddress::getCurrencyChain, currencyChain)
                .oneOpt().orElseThrow();
        String operatorWallet = operatorConfig.getWalletAddress();
        if (StringUtils.isBlank(operatorWallet)) {
            log.error("gas2operator error, operatorWallet is empty");
            taskRecord.setStatus(WalletTransferStatus.FAILED);
            taskRecord.setFinishTime(new Date());
            taskRecord.setRemark("operator wallet is empty");
            walletCollectTaskRecordService.updateById(taskRecord);
//            return "error:operatorWallet is empty";
            return false;
        }

        ConfigWalletAddress gasConfig = configWalletAddressService.lambdaQuery()
                .eq(ConfigWalletAddress::getWalletType, WalletType.GAS.getCode())
                .eq(ConfigWalletAddress::getCurrencyChain, currencyChain)
                .oneOpt().orElseThrow();
        String gasWallet = gasConfig.getWalletAddress();
        if (StringUtils.isBlank(gasWallet)) {
            log.error("gas2operator error, gasWallet is empty");
//            return "error:gasWallet is empty";
            taskRecord.setStatus(WalletTransferStatus.FAILED);
            taskRecord.setFinishTime(new Date());
            taskRecord.setRemark("gas wallet is empty");
            walletCollectTaskRecordService.updateById(taskRecord);
            return false;
        }

        BigDecimal supplyAmount = configWalletCollect.getSupplyAmount();
        BigDecimal balanceLimit = configWalletCollect.getBalanceLimit();
        Result<BigDecimal> ethBalance = feignEthService.getBalance(operatorWallet, CurrencyEnum.ETH.id);
        if (ethBalance != null && ethBalance.getCode() == ResultCodeEnum.SUCCESS.getCode()) {
            BigDecimal eth = ethBalance.getData();
            if (eth.compareTo(balanceLimit) >= 0) {
                //不需要补充gas
                log.info("operator wallet eth:{} > balanceLimit:{} ,不需要补充gas", eth, balanceLimit);
//                return "success:no need to transfer";
                taskRecord.setStatus(WalletTransferStatus.SUCCESS);
                taskRecord.setFinishTime(new Date());
                taskRecord.setRemark("gas enough. no need to transfer gas");
                walletCollectTaskRecordService.updateById(taskRecord);
                return true;
            }
        } else {
            log.error("gas2operator error, ethBalance is empty");
            taskRecord.setStatus(WalletTransferStatus.FAILED);
            taskRecord.setFinishTime(new Date());
            taskRecord.setRemark("operator wallet eth balance error");
            walletCollectTaskRecordService.updateById(taskRecord);
//            return "error:operator wallet eth balance error";
            return false;
        }

        Result<BigDecimal> gasResult = feignEthService.getBalance(gasWallet, CurrencyEnum.ETH.id);
        if (gasResult != null && gasResult.getCode() == ResultCodeEnum.SUCCESS.getCode()) {
            BigDecimal gas = gasResult.getData();
            if (gas.compareTo(supplyAmount) < 0) {
                log.error("gas2operator error, gas wallet eth :{} < supplyAmount :{}", gas, supplyAmount);
                taskRecord.setStatus(WalletTransferStatus.FAILED);
                taskRecord.setFinishTime(new Date());
                taskRecord.setRemark("gas wallet eth. Insufficient balance");
                walletCollectTaskRecordService.updateById(taskRecord);
//                return "error:gas wallet eth. Insufficient balance";
                return false;
            }
        } else {
            log.error("gas2operator error, ethBalance is empty");
            taskRecord.setStatus(WalletTransferStatus.FAILED);
            taskRecord.setFinishTime(new Date());
            taskRecord.setRemark("gas wallet eth balance error");
            walletCollectTaskRecordService.updateById(taskRecord);
//            return "error:gas wallet eth balance error";
            return false;
        }

        String tradeNo = taskRecord.getTradeNo();
        WalletTransferRecord transferRecord = new WalletTransferRecord();
        transferRecord.setTradeNo(tradeNo);
        transferRecord.setCurrencyChain(currencyChain);
        transferRecord.setFromAddress(gasWallet);
        transferRecord.setToAddress(operatorWallet);
        transferRecord.setAmount(supplyAmount);
//        transferRecord.setFee();
        transferRecord.setCurrencyId(CurrencyEnum.ETH.id);
        transferRecord.setStatus(WalletTransferStatus.PENDING);
        transferRecord.setTradeType(TradeType.GAS_TO_OPERATOR);
        walletTransferRecordService.save(transferRecord);

        String hash = null;
        try {
            hash = chainTransferDealService.transferOnChain(CurrencyEnum.ETH.id, currencyChain, gasWallet, operatorWallet, supplyAmount);
        } catch (Exception e) {
            log.error("",e);
        }
        if (StringUtils.isNotBlank(hash)) {
            log.info("transfer2store hash: {}", hash);
            transferRecord.setTxHash(hash);
            walletTransferRecordService.updateById(transferRecord);

            taskRecord.setStatus(WalletTransferStatus.WAITING);
            taskRecord.setFinishTime(new Date());
            taskRecord.setRemark("success:hash "+hash);
            walletCollectTaskRecordService.updateById(taskRecord);
//            return "success:hash "+hash;
            return false;
        } else {
            log.error("transfer2store error");
            transferRecord.setStatus(WalletTransferStatus.FAILED);
            transferRecord.setFinishTime(new Date());
            walletTransferRecordService.updateById(transferRecord);

            taskRecord.setStatus(WalletTransferStatus.FAILED);
            taskRecord.setFinishTime(new Date());
            taskRecord.setRemark("transfer failed");
            walletCollectTaskRecordService.updateById(taskRecord);
//            return "error:transfer failed";
            return false;
        }
    }

    public void result(String tradeNo, Integer status) {
        WalletCollectTaskRecord taskRecord = walletCollectTaskRecordService.updateResult(tradeNo, status);
        Integer dependTaskId = taskRecord.getDependTaskId();
        if (Objects.equals(status, WalletTransferStatus.SUCCESS) && dependTaskId != null) {
            rabbitTemplate.convertAndSend(QueueConstant.AFTER_GAS_TRANSFER, dependTaskId);
        }
    }
}
