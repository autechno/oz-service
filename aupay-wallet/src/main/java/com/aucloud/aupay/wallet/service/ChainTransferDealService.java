package com.aucloud.aupay.wallet.service;

import com.alibaba.fastjson2.JSON;
import com.aucloud.aupay.wallet.feign.BitcoinClient;
import com.aucloud.aupay.wallet.feign.FeignEthContractService;
import com.aucloud.aupay.wallet.feign.FeignEthService;
import com.aucloud.aupay.wallet.feign.TronClient;
import com.aucloud.constant.CurrencyEnum;
import com.aucloud.constant.ResultCodeEnum;
import com.aucloud.exception.ServiceRuntimeException;
import com.aucloud.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Slf4j
@Service
@Transactional
public class ChainTransferDealService {

    @Autowired
    private FeignEthContractService feignEthContractService;

    @Autowired
    private BitcoinClient bitcoinClient;

    @Autowired
    private FeignEthService feignEthService;

    @Autowired
    private TronClient tronClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;

//    @Transactional
//    public void transfer(TransferRequest transferRequest) {
//        String transactionId = transferRequest.getTransactionId();
//        String businessId = transferRequest.getBusinessId();
//        Integer tradeType = transferRequest.getTradeType();
//        String toAddress = transferRequest.getToAddress();
//        BigDecimal amount = transferRequest.getAmount();
//        String fromWalletId = transferRequest.getWalletId().toString();//from钱包id，即提币钱包
//        try {
//            AupayDigitalCurrencyWallet aupayDigitalCurrencyWallet = walletService.getWalletById(fromWalletId);//转出钱包
//            String fromAddress = aupayDigitalCurrencyWallet.getAddress();
//            Integer currencyId = aupayDigitalCurrencyWallet.getCurrencyId();
//            Integer currencyChain = aupayDigitalCurrencyWallet.getCurrencyChain();
////            BigDecimal balance = aupayDigitalCurrencyWallet.getBalance();//钱包余额
//            BigDecimal balance = walletService.getRealBalance(aupayDigitalCurrencyWallet,currencyId,currencyChain);//改为 链上实时钱包
//            log.info("aupayDigitalCurrencyWallet real balance on chain : {}", balance);
//            if(balance.compareTo(amount) < 0) {
//                throw new ServiceRuntimeException(ResultCodeEnum.INSUFFICIENT_BALANCE.getLabel_zh_cn(), ResultCodeEnum.INSUFFICIENT_BALANCE.getCode());
//            }
////            if(!tradeType.equals(WalletTransferTradeType.USER_ASSETS_COLLECTION)){
////                //用户资金归集的时候 不能扣减 钱包余额
////                int addBalanceCount = walletService.addBalance(fromWalletId, amount.negate());
////                log.info("addBalanceCount:[{}]",addBalanceCount);
////            }
//            String txId = transferOnChain(currencyId, currencyChain, fromAddress, toAddress, amount);
//            log.info("after call chain .... return txId : {}", txId);
//
//            if(tradeType.equals(WalletTransferTradeType.WITHDRAW)) {
//                log.info("资产钱包进行检测 并且将交易id同步到提币记录中");
//                WithdrawProcess withdrawProcess = new WithdrawProcess();
//                withdrawProcess.setWithdrawId(businessId);
//                withdrawProcess.setChainTxId(txId);
//                rabbitTemplate.convertAndSend(QueueConstant.WITHDRAW_PROCESS, JSONObject.toJSONString(withdrawProcess));//提币处理
////                rabbitTemplate.convertAndSend(QueueConstant.CHECK_WITHDRAW_ADDRESS_ASSETS, fromWalletId);//提币钱包 ,此次应该放在提币之前吧
//            }
//            //生成转账记录 并进行到账监听 到账后根据不同类型进行处理
//            AupayWalletTransferRecord aupayWalletTransferRecord = new AupayWalletTransferRecord();
//            aupayWalletTransferRecord.setId(IdGenUtil.getUUID());
//            aupayWalletTransferRecord.setTransactionId(transactionId);
//            aupayWalletTransferRecord.setBusinessId(businessId);
//            aupayWalletTransferRecord.setCreateTime(new Date());
//            aupayWalletTransferRecord.setCurrencyId(aupayDigitalCurrencyWallet.getCurrencyId());
//            aupayWalletTransferRecord.setCurrencyChain(aupayDigitalCurrencyWallet.getCurrencyChain());
//            aupayWalletTransferRecord.setFromAddress(fromAddress);
//            aupayWalletTransferRecord.setFromWalletId(fromWalletId);
//            aupayWalletTransferRecord.setTradeType(tradeType);
//            aupayWalletTransferRecord.setFromUserId(transferRequest.getFromUserId());
//            aupayWalletTransferRecord.setChainTxId(txId);
//            aupayWalletTransferRecord.setToAddress(toAddress);
//            aupayWalletTransferRecord.setToUserId(transferRequest.getToUserId());
//            aupayWalletTransferRecord.setAmount(amount);
//            aupayWalletTransferRecord.setFromBeforeBalance(balance);
//            aupayWalletTransferRecord.setFromAfterBalance(balance.subtract(amount));
//            aupayWalletTransferRecord.setState(WalletTransferRecordState.PENDING);
//            String toWalletId = transferRequest.getToWalletId()==null?null:transferRequest.getToWalletId().toString();
//            aupayWalletTransferRecord.setToWalletId(toWalletId);
////            if (StringUtils.isNotBlank(toWalletId)) {
////                AupayDigitalCurrencyWallet toWallet = walletService.getWalletById(toWalletId);
////                BigDecimal tobalance1 = toWallet.getBalance() == null ? BigDecimal.ZERO : toWallet.getBalance();
////                aupayWalletTransferRecord.setToBeforeBalance(tobalance1);
////                aupayWalletTransferRecord.setToAfterBalance(tobalance1.add(amount));
////            }
//            int count = walletService.saveAupayWalletTransferRecord(aupayWalletTransferRecord);
//            log.info("saveAupayWalletTransferRecord count : {}", count);
//        } catch (Exception e) {
//            log.error("transfer deal fail。", e);
//            throw new RuntimeException(e);
//        }
//    }

    /**链上转账交易**/
    public String transferOnChain(Integer currencyId, Integer currencyChain, String fromAddress, String toAddress, BigDecimal amount) {
        String txId = null;
        if(currencyChain.equals(CurrencyEnum.CurrencyChainEnum.TRC.id)) {
            Result<String> result = tronClient.transfer(currencyId,fromAddress,toAddress,amount);
            if(!result.getCode().equals(ResultCodeEnum.SUCCESS.getCode())) {
                throw new ServiceRuntimeException(ResultCodeEnum.FAIL.getLabel_zh_cn(), ResultCodeEnum.FAIL.getCode());
            }
            txId = result.getData();
        } else if(currencyChain.equals(CurrencyEnum.CurrencyChainEnum.BTC.id)) {
            Result<String> result = bitcoinClient.transfer(currencyId,fromAddress,toAddress,amount);
            if(!result.getCode().equals(ResultCodeEnum.SUCCESS.getCode())) {
                throw new ServiceRuntimeException(ResultCodeEnum.FAIL.getLabel_zh_cn(), ResultCodeEnum.FAIL.getCode());
            }
            txId = result.getData();
        } else if(currencyChain.equals(CurrencyEnum.CurrencyChainEnum.ETH.id)) {
            Result<String> result = feignEthService.transfer(currencyId,fromAddress,toAddress,amount);
            log.info("etherenumClient.transfer, return : {}", JSON.toJSONString(result));
            if(!result.getCode().equals(ResultCodeEnum.SUCCESS.getCode()) || result.getData() == null) {
                throw new ServiceRuntimeException(ResultCodeEnum.FAIL.getLabel_zh_cn(), ResultCodeEnum.FAIL.getCode());
            }
            txId = result.getData();
        }
        return txId;
    }

    public BigDecimal getWalletBalance(String address, Integer currencyId) {
        BigDecimal balance;
        Result<BigDecimal> result = feignEthContractService.getBalance(address, currencyId);
        if (result != null && result.getCode().equals(ResultCodeEnum.SUCCESS.getCode())) {
            balance = result.getData();
        } else {
            log.error("getWalletBalance error, wallet balance is empty");
            throw new ServiceRuntimeException(ResultCodeEnum.WALLET_NOT_EXISTS);
        }
        return balance;
    }

}
