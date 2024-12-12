package com.aucloud.eth.service;

import com.aucloud.commons.constant.CurrencyEnum;
import com.aucloud.commons.constant.ResultCodeEnum;
import com.aucloud.eth.contracts.WalletManagerContract;
import com.aucloud.eth.feign.FeignWalletService;
import com.aucloud.commons.exception.ServiceRuntimeException;
import com.aucloud.commons.pojo.dto.EthTransactionCallback;
import com.aucloud.commons.pojo.dto.WithdrawBatchDto;
import com.aucloud.commons.utils.Tools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.EthFeeHistory;
import org.web3j.protocol.core.methods.response.EthMaxPriorityFeePerGas;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticEIP1559GasProvider;
import org.web3j.utils.Convert;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RefreshScope
@Slf4j
@Service
public class AupayWalletManagerService {

    private static final String password = "c5457a9017e7";
//    private static final String path = "/Users/yangli/Documents/ethwallets";

    private String privateKey;

    @Value("${wallet.manager.contract.address}")
    private String walletManagerContractAddress;
    @Autowired
    private ContractProperties contractProperties;
    @Autowired
    private Web3jClientService web3jclientService;

    @Value("${wallet.file.path:/Users/yangli/Documents/ethwallets}")
    private String path;
    @Value("${wallet.file.suffix:/Users/yangli/Documents/ethwallets}")
    private String suffix;
    @Autowired
    private FeignWalletService feignWalletService;

    @Value("${wallet.manager.operator.address}")
    public void setPrivateKey(String operatorAddr) {
        try {
            File file = new File(path, operatorAddr+suffix);
            Credentials credentials = WalletUtils.loadCredentials(password, file);
            privateKey = credentials.getEcKeyPair().getPrivateKey().toString(16);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceRuntimeException(ResultCodeEnum.FAIL);
        }
    }

    public String generateOperator() {
        String address = "";
        try {
            String filename = WalletUtils.generateNewWalletFile(password, new File(path));
            File file = new File(path + "/" + filename);
            // 加载钱包的凭证
            Credentials credentials = WalletUtils.loadCredentials(password, file);
            // 获取钱包的以太坊地址和私钥
            address = credentials.getAddress();
            boolean b = file.renameTo(new File(path + "/" + address+suffix));
//            System.out.println("Wallet address: " + credentials.getAddress());
//            System.out.println("Wallet password: " + walletPassword);
//            System.out.println("Wallet private key: " + credentials.getEcKeyPair().getPrivateKey().toString(16));
//            System.out.println("Wallet file created: " + walletFileName);
        } catch (Exception e) {
           log.error(e.getMessage(), e);
           throw new ServiceRuntimeException(ResultCodeEnum.FAIL);
        }
        return address;
    }

    public List<String> getAllUserWallets() throws Exception {
        return getWalletManagerContract().getUserWallets().send();
    }

    public List<String> createUserWallet(int count) throws Exception {
        WalletManagerContract walletManagerContract = getWalletManagerContract();
        BigInteger userWalletsNumbers = walletManagerContract.getUserWalletsNumbers().send();
        TransactionReceipt receipt = walletManagerContract.generateUserWalletBatch(BigInteger.valueOf(count)).send();
        return walletManagerContract.getSubUserWallets(userWalletsNumbers).send();
    }

    public String recycleUserWallet(String address) throws Exception {
        WalletManagerContract walletManagerContract = getWalletManagerContract();
        TransactionReceipt receipt = walletManagerContract.recycleUserWallet(address).send();
        if (receipt.isStatusOK()) {
            return receipt.getTransactionHash();
        }
        throw new ServiceRuntimeException(ResultCodeEnum.FAIL);
    }

    public BigDecimal getWalletBalance(String address, CurrencyEnum currencyEnum) throws Exception {
        String token = contractProperties.getContractAddress(currencyEnum);
        WalletManagerContract walletManagerContract = getWalletManagerContract();
        BigInteger balance = walletManagerContract.balanceOfToken(address, token).send();
        return new BigDecimal(balance);
    }

    public String withdrawBatch(WithdrawBatchDto dto) throws Exception {
        String innerHash = Tools.generateRandomString(32);
        List<WalletManagerContract.WithdrawMeta> wdArr = new ArrayList<>();
        dto.getAddressAmounts().forEach((k,v) -> {
            WalletManagerContract.WithdrawMeta meta = new WalletManagerContract.WithdrawMeta(k, v.toBigInteger());
            wdArr.add(meta);
        });
        WalletManagerContract walletManagerContract = getWalletManagerContract();
        RemoteFunctionCall<TransactionReceipt> call;
        if (dto.getCurrencyEnum() == CurrencyEnum.ETH) {
            call = walletManagerContract.withdrawETHBatch(wdArr);
        } else {
            String token = contractProperties.getContractAddress(dto.getCurrencyEnum());
            call = walletManagerContract.withdrawTokenBatch(token, wdArr);
        }
        CompletableFuture<String> completableFuture = call.sendAsync().thenApply(TransactionReceipt::getTransactionHash);

        async(innerHash, completableFuture);
        return innerHash;
    }

    public String user2collect(CurrencyEnum currencyEnum, BigDecimal limit) throws Exception {
        String innerHash = Tools.generateRandomString(32);
        String token = contractProperties.getContractAddress(currencyEnum);
        WalletManagerContract walletManagerContract = getWalletManagerContract();
        RemoteFunctionCall<TransactionReceipt> call = walletManagerContract.collect(Stream.of(new WalletManagerContract.CollectMeta(token, limit.toBigInteger())).toList());
        CompletableFuture<String> completableFuture = call.sendAsync().thenApply(TransactionReceipt::getTransactionHash);

        async(innerHash, completableFuture);
        return innerHash;
    }

    public String collect2withdraw(CurrencyEnum currencyEnum, BigDecimal limit) throws Exception {
        String innerHash = Tools.generateRandomString(32);
        String token = contractProperties.getContractAddress(currencyEnum);
        WalletManagerContract walletManagerContract = getWalletManagerContract();
        RemoteFunctionCall<TransactionReceipt> call = walletManagerContract.collect2withdraw(Stream.of(new WalletManagerContract.CollectMeta(token, limit.toBigInteger())).toList());
        CompletableFuture<String> completableFuture = call.sendAsync().thenApply(TransactionReceipt::getTransactionHash);

        async(innerHash, completableFuture);
        return innerHash;
    }

    public String collect2storage(String storeWallet, CurrencyEnum currencyEnum, BigDecimal limit) throws Exception {
        String innerHash = Tools.generateRandomString(32);
        String token = contractProperties.getContractAddress(currencyEnum);
        WalletManagerContract walletManagerContract = getWalletManagerContract();
        RemoteFunctionCall<TransactionReceipt> call = walletManagerContract.collect2storage(storeWallet,Stream.of(new WalletManagerContract.CollectMeta(token, limit.toBigInteger())).toList());
        CompletableFuture<String> completableFuture = call.sendAsync().thenApply(TransactionReceipt::getTransactionHash);

        async(innerHash, completableFuture);
        return innerHash;
    }

    private void async(String innerHash, CompletableFuture<String> completableFuture) throws Exception {
        log.info("completableFuture.thenAccept");
        completableFuture.thenAccept(transactionHash -> {
            EthTransactionCallback callback = new EthTransactionCallback();
            callback.setInnerHash(innerHash);
            callback.setHash(transactionHash);
            feignWalletService.receiptTransactionHashAsync(callback);
            try {
                listen(transactionHash, innerHash);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            log.info("Transaction Hash: {}", transactionHash);
        }).exceptionally(ex -> {
            log.error("Error: {}", ex.getMessage(), ex);
            EthTransactionCallback callback = new EthTransactionCallback();
            callback.setInnerHash(innerHash);
            callback.setStatus(false);
            callback.setError(ex.getMessage());
            feignWalletService.receiptTransactionHashAsync(callback);
            return null;
        });
    }

    private void listen(String transactionHash, String innerHash) throws Exception {
        web3jclientService.getWeb3j().ethGetTransactionReceipt(transactionHash)
                .sendAsync().thenApply(receipt -> {
                    log.info("receipt: {}", receipt);
                    EthTransactionCallback callback = new EthTransactionCallback();
                    callback.setInnerHash(innerHash);
                    callback.setHash(transactionHash);
                    callback.setStatus(false);
                    if (receipt.getTransactionReceipt().isPresent()) {
                        TransactionReceipt transactionReceipt = receipt.getTransactionReceipt().get();
                        boolean statusOK = transactionReceipt.isStatusOK();
                        if (statusOK) {
                            callback.setStatus(true);
                            try {
                                BigInteger gasUsed = transactionReceipt.getGasUsed();
                                if (gasUsed != null) {
                                    callback.setGasUsed(Convert.fromWei(new BigDecimal(gasUsed), Convert.Unit.ETHER));
                                }
                                BigInteger blockNumber = transactionReceipt.getBlockNumber();
                                if (blockNumber != null) {
                                    callback.setBlockNumber(new BigDecimal(blockNumber));
                                }
                            } catch (Exception e) {
                                log.error(e.getMessage(), e);
                            }
                        } else {
                            String revertReason = transactionReceipt.getRevertReason();
                            callback.setError(revertReason);
                        }
                    } else {
                        log.info("Transaction not yet confirmed.");
                    }
                    feignWalletService.postTransactionResult(callback);
                    return null;
                });
    }


    private WalletManagerContract getWalletManagerContract() throws IOException {
        Web3j web3j = web3jclientService.getWeb3j();
        RawTransactionManager transactionManager = getTransactionManager(web3j, getCredentials());
        ContractGasProvider contractGasProvider = getContractGasProvider(web3j);
        return WalletManagerContract.load(walletManagerContractAddress, web3j, transactionManager, contractGasProvider);
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

    private Credentials getCredentials() {
        return Credentials.create(privateKey);
    }
}
