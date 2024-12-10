package com.aucloud.eth.service;

import com.aucloud.constant.CurrencyEnum;
import com.aucloud.constant.ResultCodeEnum;
import com.aucloud.eth.contracts.WalletManagerContract;
import com.aucloud.eth.feign.FeignWalletService;
import com.aucloud.exception.ServiceRuntimeException;
import com.aucloud.pojo.dto.WithdrawBatchDto;
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
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthMaxPriorityFeePerGas;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticEIP1559GasProvider;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
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
        List list = getWalletManagerContract().getUserWallets().send();
        return list;
    }

    public List<String> createUserWallet(int count) throws Exception {
        WalletManagerContract walletManagerContract = getWalletManagerContract();
        BigInteger userWalletsNumbers = walletManagerContract.getUserWalletsNumbers().send();
        TransactionReceipt receipt = walletManagerContract.generateUserWalletBatch(BigInteger.valueOf(count)).send();
        List list = walletManagerContract.getSubUserWallets(userWalletsNumbers).send();
        return list;
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
        RemoteFunctionCall<TransactionReceipt> call = null;
        CompletableFuture<TransactionReceipt> future = null;
        if (dto.getCurrencyEnum() == CurrencyEnum.ETH) {
            call = walletManagerContract.withdrawETHBatch(wdArr);
        } else {
            String token = contractProperties.getContractAddress(dto.getCurrencyEnum());
            call = walletManagerContract.withdrawTokenBatch(token, wdArr);
        }
        CompletableFuture<String> completableFuture = call.sendAsync().thenApply(TransactionReceipt::getTransactionHash);

        AtomicReference<String> txHash = new AtomicReference<>();
        log.info("completableFuture.thenAccept");
        completableFuture.thenAccept(transactionHash -> {
            log.info("Transaction Hash: {}", transactionHash);
            txHash.set(transactionHash);
        }).exceptionally(ex -> {
            log.error("Error: {}", ex.getMessage(), ex);
            return null;
        });
        completableFuture.join();
        log.info("completableFuture.join end");
        String hash = completableFuture.get();
        log.info("completableFuture.get end");
        hash = txHash.get();
        return hash;
    }


    public String user2collect(CurrencyEnum currencyEnum, BigDecimal limit) throws Exception {
        String token = contractProperties.getContractAddress(currencyEnum);
        WalletManagerContract walletManagerContract = getWalletManagerContract();
        RemoteFunctionCall<TransactionReceipt> call = walletManagerContract.collect(Stream.of(new WalletManagerContract.CollectMeta(token, limit.toBigInteger())).toList());
        CompletableFuture<String> completableFuture = call.sendAsync().thenApply(TransactionReceipt::getTransactionHash);

        AtomicReference<String> txHash = new AtomicReference<>();
        log.info("completableFuture.thenAccept");
        completableFuture.thenAccept(transactionHash -> {
            try {
                listen(transactionHash, receipt -> {
                    log.info("receipt: {}", receipt);
                    if (receipt.getTransactionReceipt().isPresent()) {
                        TransactionReceipt transactionReceipt = receipt.getTransactionReceipt().get();
                        boolean statusOK = transactionReceipt.isStatusOK();
                        if (statusOK) {
                            transactionReceipt.getGasUsed();
                            String transactionHash1 = transactionReceipt.getTransactionHash();
                            BigInteger blockNumber = transactionReceipt.getBlockNumber();
                        } else {
                            String revertReason = transactionReceipt.getRevertReason();
                        }
                    } else {
                        log.info("Transaction not yet confirmed.");
                    }
                    return null;
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            log.info("Transaction Hash: {}", transactionHash);
            txHash.set(transactionHash);
        }).exceptionally(ex -> {
            log.error("Error: {}", ex.getMessage(), ex);
            return null;
        });
        completableFuture.join();
        log.info("completableFuture.join end");
        String hash = completableFuture.get();
        log.info("completableFuture.get end");
        hash = txHash.get();

        return hash;
    }

    public String collect2withdraw(CurrencyEnum currencyEnum, BigDecimal limit) throws Exception {
        String token = contractProperties.getContractAddress(currencyEnum);
        WalletManagerContract walletManagerContract = getWalletManagerContract();
        RemoteFunctionCall<TransactionReceipt> call = walletManagerContract.collect2withdraw(Stream.of(new WalletManagerContract.CollectMeta(token, limit.toBigInteger())).toList());
        CompletableFuture<String> completableFuture = call.sendAsync().thenApply(TransactionReceipt::getTransactionHash);

        AtomicReference<String> txHash = new AtomicReference<>();
        log.info("completableFuture.thenAccept");
        completableFuture.thenAccept(transactionHash -> {
            log.info("Transaction Hash: {}", transactionHash);
            txHash.set(transactionHash);
        }).exceptionally(ex -> {
            log.error("Error: {}", ex.getMessage(), ex);
            return null;
        });
        completableFuture.join();
        log.info("completableFuture.join end");
        String hash = completableFuture.get();
        log.info("completableFuture.get end");
        hash = txHash.get();
        return hash;
    }

    public String collect2storage(String storeWallet, CurrencyEnum currencyEnum, BigDecimal limit) throws Exception {
        String token = contractProperties.getContractAddress(currencyEnum);
        WalletManagerContract walletManagerContract = getWalletManagerContract();
        RemoteFunctionCall<TransactionReceipt> call = walletManagerContract.collect2storage(storeWallet,Stream.of(new WalletManagerContract.CollectMeta(token, limit.toBigInteger())).toList());
        CompletableFuture<String> completableFuture = call.sendAsync().thenApply(TransactionReceipt::getTransactionHash);

        AtomicReference<String> txHash = new AtomicReference<>();
        log.info("completableFuture.thenAccept");
        completableFuture.thenAccept(transactionHash -> {
            log.info("Transaction Hash: {}", transactionHash);
            txHash.set(transactionHash);
        }).exceptionally(ex -> {
            log.error("Error: {}", ex.getMessage(), ex);
            return null;
        }).get();
        completableFuture.join();
        log.info("completableFuture.join end");
        String hash = completableFuture.get();
        log.info("completableFuture.get end");
        hash = txHash.get();

        listen(hash, receipt -> {
            log.info("receipt: {}", receipt);
            if (receipt.getTransactionReceipt().isPresent()) {
                TransactionReceipt transactionReceipt = receipt.getTransactionReceipt().get();
                boolean statusOK = transactionReceipt.isStatusOK();
                if (statusOK) {
                    transactionReceipt.getGasUsed();
                    String transactionHash = transactionReceipt.getTransactionHash();
                    String revertReason = transactionReceipt.getRevertReason();
                    BigInteger blockNumber = transactionReceipt.getBlockNumber();
                }
            } else {
                log.info("Transaction not yet confirmed.");
            }
            return null;
        });
        return hash;
    }

    private <U> U listen(String transactionHash, Function<? super EthGetTransactionReceipt,U> function) throws Exception {
        return web3jclientService.getWeb3j().ethGetTransactionReceipt(transactionHash)
                .sendAsync().thenApply(function).get();
//                .thenApply(receipt -> {
//                    if (receipt.getTransactionReceipt().isPresent()) {
//                        System.out.println("Transaction confirmed: " + receipt.getTransactionReceipt().get().getStatus());
//                    } else {
//                        System.out.println("Transaction not yet confirmed.");
//                    }
//                    return receipt;
//                });

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
