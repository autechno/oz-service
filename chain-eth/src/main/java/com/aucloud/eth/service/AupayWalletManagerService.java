package com.aucloud.eth.service;

import com.aucloud.eth.contracts.WalletManagerContract;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.EthFeeHistory;
import org.web3j.protocol.core.methods.response.EthMaxPriorityFeePerGas;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticEIP1559GasProvider;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

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

    public void fn() throws IOException {
        RemoteFunctionCall<List> userWallets = getWalletManagerContract().getUserWallets();
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
