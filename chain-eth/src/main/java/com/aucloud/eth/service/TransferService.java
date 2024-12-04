package com.aucloud.eth.service;

import com.alibaba.fastjson.JSON;
import com.aucloud.constant.CurrencyEnum;
import com.aucloud.exception.ServiceRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Response;
import org.web3j.protocol.core.methods.response.EthFeeHistory;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthMaxPriorityFeePerGas;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class TransferService {

    @Autowired
    private ContractProperties contractProperties;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private Web3jClientService web3jClientService;

    private static final String keyPrefix = "from_address_transfer_lock_";
    private boolean acquireAddressTransferLock(String address, String uuid) {
        boolean flag = false;
        int loop = 60;//60次 大概是60秒
        while (true) {
            if (Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(keyPrefix + address, uuid, 1, TimeUnit.MINUTES))) {
                flag = true;
                break;
            } else {
                if (loop-- > 0) {
                    try {
                        Thread.sleep(1000);//睡1秒
                    } catch (InterruptedException e) {
                        log.error("", e);
                    }
                } else {
                    log.error("获取锁失败。。。from address:{}", address);
                    break;
                }
            }
        }
        log.info("钱包地址:{} 获取transfer执行锁结果:{},剩余loop次数:{}", address, flag, loop);
        return flag;
    }
    private void releaseAddressTransferLock(String address, String uuid) {
        String s = redisTemplate.opsForValue().get(keyPrefix + address);
        if (uuid.equals(s)) {
            redisTemplate.delete(keyPrefix+address);
        }
    }

    public String transferToken(String from, String to, BigDecimal value, Integer currencyId) {
        CurrencyEnum currencyEnum = CurrencyEnum.findById(currencyId);
        if (currencyEnum == null) {
            throw new RuntimeException("不支持的currencyId");
        }
        String uuid = UUID.randomUUID().toString();
        if (acquireAddressTransferLock(from, uuid)) {
            try {
                if (currencyEnum == CurrencyEnum.ETH) {
                    return transferETHToken(from,to,value,currencyEnum);
                } else {
                    return transferERC20Token(from, to,value,currencyEnum);
                }
            } catch (Exception e) {
                log.error("" ,e);
                throw new RuntimeException(e);
            } finally {
                releaseAddressTransferLock(from, uuid);
            }
        } else {
            throw new RuntimeException("获取锁失败。");
        }
    }

    private String transferERC20Token(String from, String to, BigDecimal value, CurrencyEnum currencyEnum) throws Exception {
        String contractAddress = contractProperties.getContractAddress(currencyEnum);
        log.info("transfer token currencyId:{}，对应合约地址:{}", currencyEnum, contractAddress);
        if (contractAddress == null || contractAddress.isEmpty()) {
            throw new RuntimeException("协议地址不在eth链上");
        }
        return transfer(from, to, value, currencyEnum);
    }

    private String transferETHToken(String from, String to, BigDecimal value, CurrencyEnum currencyEnum) throws Exception {
        return transfer(from, to, value, currencyEnum);
    }

    private String getFunctionEncode(String to, BigDecimal value, CurrencyEnum currencyEnum) {
        BigInteger val = value.multiply(new BigDecimal("10").pow(currencyEnum.precision)).toBigInteger();// 单位换算
        log.info("transfer amount:{} token", val);
        Function function = new Function(
                "transfer",
                Arrays.asList(new Address(to), new Uint256(val)),
                Collections.singletonList(new TypeReference<Bool>() {
                }));
        if (currencyEnum == CurrencyEnum.USDT) {
            function = new Function(
                    "transfer",
                    Arrays.asList(new Address(to), new Uint256(val)),
                    Collections.emptyList());
        }
        //创建交易对象
        return FunctionEncoder.encode(function);
    }

    private String transfer(String from, String to, BigDecimal value, CurrencyEnum currencyEnum) throws Exception{
//        WalletFile walletFile = FileUtils.readWalletFile(from);
//        if (walletFile == null) {
//            throw new RuntimeException("根据地址获取钱包文件失败.");
//        }
//        String privateKey = walletFile.getPrivateKey();
        String privateKey = "";
        log.info("钱包:{}, privateKey:{}", from, privateKey);
        //转账的凭证，需要传入私钥
        Credentials credentials = Credentials.create(privateKey);
        //获取交易笔数
        EthGetTransactionCount ethGetTransactionCount = web3jClientService.getWeb3j().ethGetTransactionCount(from, DefaultBlockParameterName.PENDING).send();
        BigInteger nonce = ethGetTransactionCount.getTransactionCount();
        log.info("nonce:{}", nonce);
        //手续费
        // 4. 获取当前的基础费用（base fee）
        EthFeeHistory ethFeeHistory = web3jClientService.getWeb3j().ethFeeHistory(1, DefaultBlockParameterName.LATEST, null).send();
        BigInteger baseFee = ethFeeHistory.getFeeHistory().getBaseFeePerGas().get(0);
        log.info("baseFee:{}", baseFee);
        EthMaxPriorityFeePerGas ethMaxPriorityFeePerGas = web3jClientService.getWeb3j().ethMaxPriorityFeePerGas().send();
        BigInteger maxPriorityFeePerGas = ethMaxPriorityFeePerGas.getMaxPriorityFeePerGas();
        log.info("maxPriorityFeePerGas:{}", maxPriorityFeePerGas);
        BigInteger maxFeePerGas = baseFee.add(maxPriorityFeePerGas);
        log.info("maxFeePerGas:{}", maxFeePerGas);

        long chainId = contractProperties.getChainId();
        log.info("chainId:{}", chainId);

//        EthGasPrice ethGasPrice = web3j.ethGasPrice().send();
//        BigInteger gasPrice = ethGasPrice.getGasPrice();
//        BigInteger priorityFee = Convert.toWei("2", Convert.Unit.GWEI).toBigInteger();//gasprice提高1Gwei应该更容易成交吧
//        gasPrice = gasPrice.add(priorityFee);
//        log.info("gasPrice:{}", gasPrice);
        //注意手续费的设置，这块很容易遇到问题
        BigInteger gasLimit = BigInteger.valueOf(70000L);
        RawTransaction rawTransaction = null;
//        String transHash = null;
        if (currencyEnum == CurrencyEnum.ETH) {
            // 5. 转换ETH到Wei单位
//            BigDecimal valueWei = Convert.toWei(value, Convert.Unit.ETHER);
            BigInteger amountInWei = Convert.toWei(value, Convert.Unit.ETHER).toBigInteger();
            log.info("transfer amount:{} wei", amountInWei);
//            rawTransaction = RawTransaction.createEtherTransaction(
//                    nonce,
//                    gasPrice, // Gas价格（20 Gwei）
//                    gasLimit, // Gas限制
//                    to,
//                    amountInWei);

            rawTransaction = RawTransaction.createEtherTransaction(
                    chainId,
                    nonce,
                    gasLimit,
                    to,
                    amountInWei,
                    maxPriorityFeePerGas,
                    maxFeePerGas
            );
//            TransactionReceipt transactionReceipt = Transfer.sendFundsEIP1559(web3j, credentials, to, valueWei, Convert.Unit.WEI, gasLimit, gasPrice, priorityFee).send();
//            transHash = transactionReceipt.getTransactionHash();
        } else {
            String encodedFunction = getFunctionEncode(to, value, currencyEnum);
//            rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit,
//                    getContractAddress(currencyEnum), encodedFunction);
            rawTransaction = RawTransaction.createTransaction(
                    chainId,
                    nonce,
                    gasLimit,
                    contractProperties.getContractAddress(currencyEnum),
                    BigInteger.ZERO,
                    encodedFunction,
                    maxPriorityFeePerGas,
                    maxFeePerGas
            );
//            RawTransactionManager rawTransactionManager = new RawTransactionManager(web3j, credentials, chainId);
//            EthSendTransaction transaction = rawTransactionManager.sendTransactionEIP1559(gasPrice, priorityFee, gasLimit, getContractAddress(currencyEnum), encodedFunction, BigInteger.ZERO);
//            String result = transaction.getTransactionHash();
//            Response.Error error = transaction.getError();
//            log.info("getError : {}", JSON.toJSONString(transaction.getError()));
        }
        //进行签名操作 //发起交易
        byte[] signMessage = TransactionEncoder.signMessage(rawTransaction, chainId, credentials);
        String hexValue = Numeric.toHexString(signMessage);
        EthSendTransaction ethSendTransaction = web3jClientService.getWeb3j().ethSendRawTransaction(hexValue).sendAsync().get();
        String transHash = ethSendTransaction.getTransactionHash();
        log.info("转账交易发起返回结果hash，txId：{}",transHash);
        if (StringUtils.isBlank(transHash)) {
            log.info("getResult : {}", ethSendTransaction.getResult());
            if (ethSendTransaction.hasError()) {
                Response.Error error = ethSendTransaction.getError();
                log.error("error : {}", JSON.toJSONString(error));
                if (error!=null) {
                    throw new ServiceRuntimeException(error.getMessage(), error.getCode());
                }
            }
        }
        return transHash;
    }
}
