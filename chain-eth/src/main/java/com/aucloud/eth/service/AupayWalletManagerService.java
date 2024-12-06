package com.aucloud.eth.service;

import com.aucloud.constant.CurrencyEnum;
import com.aucloud.eth.contracts.WalletManagerContract;
import com.aucloud.pojo.dto.WithdrawBatchDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.EthFeeHistory;
import org.web3j.protocol.core.methods.response.EthMaxPriorityFeePerGas;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticEIP1559GasProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RefreshScope
@Slf4j
@Service
public class AupayWalletManagerService {

    @Value("${address.web3j.url}")
    private String nodeEndpoint;
    @Value("${web3j.eth.node}")
    private String privateKey;
    @Value("${web3j.eth.node}")
    private String walletManagerContractAddress;

    private ContractProperties contractProperties;

    public void fn() throws IOException {
        RemoteFunctionCall<List> userWallets = getWalletManagerContract().getUserWallets();
    }

    public BigDecimal getWalletBalance(String address, CurrencyEnum currencyEnum) throws Exception {
        String token = contractProperties.getContractAddress(currencyEnum);
        WalletManagerContract walletManagerContract = getWalletManagerContract();
        BigInteger balance = walletManagerContract.balanceOfToken(address, token).send();
        return new BigDecimal(balance);
    }

    public String withdrawBatch(WithdrawBatchDto dto) throws Exception {
        List<WalletManagerContract.WithdrawMeta> wdArr = new ArrayList<>();
        dto.getAddressAmounts().forEach((k,v) -> {
            WalletManagerContract.WithdrawMeta meta = new WalletManagerContract.WithdrawMeta(k, v.toBigInteger());
            wdArr.add(meta);
        });
        WalletManagerContract walletManagerContract = getWalletManagerContract();
        CompletableFuture<TransactionReceipt> future = null;
        if (dto.getCurrencyEnum() == CurrencyEnum.ETH) {
            future = walletManagerContract.withdrawETHBatch(wdArr).sendAsync();
        } else {
            String token = contractProperties.getContractAddress(dto.getCurrencyEnum());
            future = walletManagerContract.withdrawTokenBatch(token, wdArr).sendAsync();
        }
        future.isDone();
        TransactionReceipt transactionReceipt = future.get();
        transactionReceipt.isStatusOK();
        String status = transactionReceipt.getStatus();
        return transactionReceipt.getTransactionHash();
    }

    public WalletManagerContract getWalletManagerContract() throws IOException {
        Web3j web3j = getWeb3j();
        Credentials credentials = getCredentials(getPrivateKey("lstake"));
        RawTransactionManager transactionManager = getTransactionManager(web3j, credentials);
        ContractGasProvider contractGasProvider = getContractGasProvider(web3j);
        return WalletManagerContract.load(walletManagerContractAddress, web3j, transactionManager, contractGasProvider);
    }


    private Web3j getWeb3j() {
        return Web3j.build(new HttpService(nodeEndpoint));
    }
    private RawTransactionManager getTransactionManager(Web3j web3j,Credentials credentials) {
        return new RawTransactionManager(web3j, credentials);
    }
    private ContractGasProvider getContractGasProvider(Web3j web3j) throws IOException {
        BigInteger chainId = web3j.ethChainId().send().getChainId();
        BigInteger gasLimit = BigInteger.valueOf(10000000);
        // 4. 获取当前的基础费用（base fee）
        EthFeeHistory ethFeeHistory = web3j.ethFeeHistory(1, DefaultBlockParameterName.LATEST, null).send();
        BigInteger baseFee = ethFeeHistory.getFeeHistory().getBaseFeePerGas().get(0);
        log.info("baseFee:{}", baseFee);
        EthMaxPriorityFeePerGas ethMaxPriorityFeePerGas = web3j.ethMaxPriorityFeePerGas().send();
        BigInteger maxPriorityFeePerGas = ethMaxPriorityFeePerGas.getMaxPriorityFeePerGas();
        log.info("maxPriorityFeePerGas:{}", maxPriorityFeePerGas);
        BigInteger maxFeePerGas = baseFee.add(maxPriorityFeePerGas);
        log.info("maxFeePerGas:{}", maxFeePerGas);
        return new StaticEIP1559GasProvider(chainId.longValue(),maxFeePerGas,maxPriorityFeePerGas,gasLimit);
    }
    private Credentials getCredentials(String privateKey) {
        return Credentials.create(privateKey);
    }
    private String getPrivateKey(String contractType) {
        return privateKey;
    }
}
