package com.aucloud.eth.service;

import com.aucloud.constant.CurrencyEnum;
import com.aucloud.constant.TxStatus;
import com.aucloud.entity.TxInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@RefreshScope
@Service
public class RpcService {

    @Autowired
    private ContractProperties contractProperties;
    @Autowired
    private Web3jClientService web3jClientService;

    private final static String ADDRESS_PREFIX = "0x";

    private final static String KEY_PREFIX = "0x";

//    private static final Long chainId = 11155111L;//Sepolia 测试网的 chainId 是 11155111;以太坊主网：chainId 为 1

//    private final static ImmutableList<ChildNumber> BIP44_ETH_ACCOUNT_ZERO_PATH =
//            ImmutableList.of(new ChildNumber(44, true), new ChildNumber(60, true),
//                    ChildNumber.ZERO_HARDENED, ChildNumber.ZERO);
//
//
//    public AupayDigitalCurrencyWallet createWallet() {
//        AupayDigitalCurrencyWallet aupayDigitalCurrencyWallet = new AupayDigitalCurrencyWallet();
//        SecureRandom secureRandom = new SecureRandom();
//        byte[] entropy = new byte[DeterministicSeed.DEFAULT_SEED_ENTROPY_BITS / 8];
//        secureRandom.engineNextBytes(entropy);
//        //生成12位助记词
//        List<String> str = null;
//        try {
//            str = MnemonicCode.INSTANCE.toMnemonic(entropy);
//        } catch (MnemonicException.MnemonicLengthException e) {
//            log.error("", e);
//        }
//        //使用助记词生成钱包种子
//        byte[] seed = MnemonicCode.toSeed(str, "");
//        DeterministicKey masterPrivateKey = HDKeyDerivation.createMasterPrivateKey(seed);
//        DeterministicHierarchy deterministicHierarchy = new DeterministicHierarchy(masterPrivateKey);
//        DeterministicKey deterministicKey = deterministicHierarchy.deriveChild(BIP44_ETH_ACCOUNT_ZERO_PATH, false, true, new ChildNumber(0));
//        byte[] bytes = deterministicKey.getPrivKeyBytes();
//        ECKeyPair keyPair = ECKeyPair.create(bytes);
//        //通过公钥生成钱包地址
//        String address = ADDRESS_PREFIX + Keys.getAddress(keyPair.getPublicKey());
//        String privateKey = KEY_PREFIX + keyPair.getPrivateKey().toString(16);
//        aupayDigitalCurrencyWallet.setIsDel(Boolean.FALSE);
//        aupayDigitalCurrencyWallet.setCreateTime(new Date());
//        aupayDigitalCurrencyWallet.setAddress(address);
//        String walletFile = FileUtils.createWalletFile(CurrencyEnum.ETH, address, privateKey);
////        aupayDigitalCurrencyWallet.setPath(walletFile);
////        aupayDigitalCurrencyWallet.setBalance(BigDecimal.ZERO);
////        aupayDigitalCurrencyWallet.setFeeBalance(BigDecimal.ZERO);
//        return aupayDigitalCurrencyWallet;
//    }
//
//    /*public void importWallet(String address, String privateKey) {
//        // 私钥 (64 个字符的十六进制字符串)
//        // 从私钥生成 ECKeyPair
//        ECKeyPair keyPair = ECKeyPair.create(new java.math.BigInteger(privateKey, 16));
//        // 从 ECKeyPair 获取钱包凭证
//        Credentials credentials = Credentials.create(keyPair);
//        // 指定钱包文件存放路径
//        String walletDirectory = "path/to/wallet/directory"; // 替换为实际路径
//        // 设置钱包文件的密码
//        String walletPassword = "你的密码";
//        // 生成钱包文件
//        String walletFileName = WalletUtils.generateWalletFile(walletPassword, credentials.getEcKeyPair(), new File(walletDirectory), false);
//        // 输出生成的钱包文件名
//        System.out.println("Wallet file generated: " + walletFileName);
//    }*/
//    public void importWallet(String address, String privateKey) {
//        FileUtils.createWalletFile(CurrencyEnum.ETH, address, privateKey);
//    }



    /**
     * 获取ERC-20 token指定地址余额
     *
     * @param address         查询地址
     * @param currencyId 合约类型
     * @return 余额
     */
    public BigDecimal getERC20Balance(String address, Integer currencyId) throws Exception {
        log.info("getERC20Balance address:{}, currencyId:{}", address, currencyId);
        CurrencyEnum c = CurrencyEnum.findById(currencyId);
        if (c == null) {
            throw new RuntimeException("不支持的合约类型");
        }
        if (c == CurrencyEnum.ETH) {
            return getEthBalance(address);
        } else {
            return getERC20Balance(address, c);
        }
    }

    private BigDecimal getERC20Balance(String address, CurrencyEnum c) throws Exception {
        String contractAddress = contractProperties.getContractAddress(c);
        if (contractAddress == null || contractAddress.isEmpty()) {
            throw new RuntimeException("不支持的合约类型");
        }
        // 创建balanceOf方法的函数对象
        Function function = new Function(
                "balanceOf",
                Collections.singletonList(new Address(address)),
                Collections.singletonList(new org.web3j.abi.TypeReference<Uint256>() {})
        );

        String data = FunctionEncoder.encode(function);
        log.info("getERC20Balance from:{} to:{} data:{}", address, contractAddress, data);
        Transaction transaction = Transaction.createEthCallTransaction(address, contractAddress, data);
        EthCall ethCall = web3jClientService.getWeb3j().ethCall(transaction, DefaultBlockParameterName.LATEST).send();
        List<Type> results = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());
//            Integer value = 0;
        if(results != null && !results.isEmpty()){
            BigInteger value = (BigInteger) results.get(0).getValue();
            //            balanceValue = new BigDecimal(value).divide(WEI, 6, RoundingMode.HALF_DOWN);
            return new BigDecimal(value).divide(
                    BigDecimal.TEN.pow(c.precision) ,
                    c.precision,
                    RoundingMode.HALF_EVEN);
        }
        return null;
    }

    private BigDecimal getEthBalance(String address) throws Exception {
        // 调用ethGetBalance来获取余额
        EthGetBalance ethGetBalance = web3jClientService.getWeb3j().ethGetBalance(address, DefaultBlockParameterName.LATEST).send();
        // 获取余额
        BigInteger wei = ethGetBalance.getBalance();
        // 将余额从Wei转换为Ether
        // 输出以太币余额
        return Convert.fromWei(wei.toString(), Convert.Unit.ETHER);
    }

    public TxInfo getTxInfo(Integer currencyId, String txId) {
        if (currencyId.equals(CurrencyEnum.ETH.id)) {
            return getEthTransactionInfoByTxId(txId);
        } else {
            CurrencyEnum currencyEnum = CurrencyEnum.findById(currencyId);
            return getTransactionInfoByTxId(txId, currencyEnum);
        }
    }

    public TxInfo getEthTransactionInfoByTxId(String txId) {
        try {
            Optional<org.web3j.protocol.core.methods.response.Transaction> optionalTransaction = web3jClientService.getWeb3j().ethGetTransactionByHash(txId).send().getTransaction();
            Optional<TransactionReceipt> optionalTransactionReceipt = web3jClientService.getWeb3j().ethGetTransactionReceipt(txId).send().getTransactionReceipt();
            if (optionalTransactionReceipt.isPresent() && optionalTransaction.isPresent()) {
                TxInfo txInfo = new TxInfo();
                txInfo.setTxId(txId);
                String from = optionalTransaction.get().getFrom();
                txInfo.setFrom(from);
                TransactionReceipt transactionReceipt = optionalTransactionReceipt.get();
                org.web3j.protocol.core.methods.response.Transaction transaction = optionalTransaction.get();

                txInfo.setDate(getBlockTimestamp(transaction.getBlockHash()));

                BigInteger value = transaction.getValue();
                BigDecimal bigDecimal = Convert.fromWei(new BigDecimal(value), Convert.Unit.ETHER);
                TxInfo.Transfer transfer = TxInfo.createTransfer(transaction.getTo(), bigDecimal);
                txInfo.setTransfer(Collections.singletonList(transfer));
                String status = transactionReceipt.getStatus();
                if (status.equals("0x1")) {
                    txInfo.setStatus(TxStatus.SUCCESS);
                } else if (status.equals("0x0")) {
                    txInfo.setStatus(TxStatus.FAILURE);
                } else {
                    txInfo.setStatus(TxStatus.PENDING);
                }
                return txInfo;
            } else {
                //暂无交易信息
                log.error("暂无交易信息, transaction hash: {}", txId);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public TxInfo getTransactionInfoByTxId(String txId, CurrencyEnum currencyEnum) {
        try {
            Optional<org.web3j.protocol.core.methods.response.Transaction> optionalTransaction = web3jClientService.getWeb3j().ethGetTransactionByHash(txId).send().getTransaction();
            if(!optionalTransaction.isPresent()) {
                log.error("链上交易查询为空。hash:{}", txId);
                return null;
            }
            org.web3j.protocol.core.methods.response.Transaction transaction = optionalTransaction.get();
//            log.info("ethGetTransactionByHash: {}", JSON.toJSONString(transaction));
            TxInfo txInfo = new TxInfo();
            txInfo.setTxId(txId);
            BigInteger gasPrice = transaction.getGasPrice();//gas price
            BigInteger gas = transaction.getGas();
            BigInteger needTransferFeeWei = gas.multiply(gasPrice);
            BigDecimal needTransferFee = Convert.fromWei(new BigDecimal(needTransferFeeWei), Convert.Unit.ETHER);
            txInfo.setTransferFee(needTransferFee);
            String from = transaction.getFrom();
            txInfo.setFrom(from);
            BigInteger txBlockNumber = null;
            try {
                txBlockNumber = transaction.getBlockNumber();
            } catch (Exception e) {
                String blockNumberRaw = transaction.getBlockNumberRaw();
                log.error("getBlockNumber() error. getBlockNumberRaw:{}",blockNumberRaw, e);
            }
            if(txBlockNumber != null) {
                BigInteger blockNumber = web3jClientService.getWeb3j().ethBlockNumber().send().getBlockNumber();
                long confirmCount = blockNumber.subtract(txBlockNumber).longValue();
                txInfo.setConfirmCount(confirmCount);
                txInfo.setBlockCount(txBlockNumber.longValue());
            }
//            String raw = transaction.getTo(); //合约地址
            txInfo.setDate(getBlockTimestamp(transaction.getBlockHash()));
            txInfo.setStatus(TxStatus.PENDING);

            Optional<TransactionReceipt> optionalTransactionReceipt = web3jClientService.getWeb3j().ethGetTransactionReceipt(txId).send().getTransactionReceipt();
            if (!optionalTransactionReceipt.isPresent()) {
                log.error("链上交易票据查询为空。hash:{}", txId);
                return null;
            }
            //BigInteger value = transaction.getValue();
            TransactionReceipt transactionReceipt = optionalTransactionReceipt.get();
//            log.info("ethGetTransactionReceipt: {}", JSON.toJSONString(transactionReceipt));
            String status = transactionReceipt.getStatus();
            List<Log> logs = transactionReceipt.getLogs();
            for(Log log : logs) {
                List<String> topics = log.getTopics();
                String event = topics.get(0);// event
                if (event.equals(contractProperties.getTransfer())) {
//                String fromAddress = topics.get(1);//from address
                    String toAddress = topics.get(2); //to address
//                    String address = log.getAddress();//合约地址
                    String value = log.getData();
                    BigDecimal amount = new BigDecimal(new BigInteger(value.substring(2), 16)).divide(
                            BigDecimal.TEN.pow(currencyEnum.precision),
                            currencyEnum.precision,
                            RoundingMode.HALF_EVEN);
                    TxInfo.Transfer transfer = TxInfo.createTransfer(hexToAddress(toAddress), amount);
                    txInfo.setTransfer(Collections.singletonList(transfer));
                    break;
                }
            }
            if(status.equals("0x1")) {
                txInfo.setStatus(TxStatus.SUCCESS);
            } else if (status.equals("0x0")) {
                txInfo.setStatus(TxStatus.FAILURE);
            } else {
                txInfo.setStatus(TxStatus.PENDING);
            }
            return txInfo;
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    private Date getBlockTimestamp(String blockHash) {
        try {
            EthBlock send = web3jClientService.getWeb3j().ethGetBlockByHash(blockHash, true).send();
            BigInteger timestamp = send.getBlock().getTimestamp();
            if (timestamp.compareTo(new BigInteger("0")) > 0) {
                return new Date(timestamp.longValue() * 1000);
            }
        } catch (Exception e) {
            log.error("获取blocktimestamp异常", e);
        }
        return null;
    }
    public static String hexToAddress(String strHex) {
        if (strHex.length() > 42) {
            if (strHex.charAt(0) == '0' && (strHex.charAt(1) == 'X' || strHex.charAt(1) == 'x')) {
                strHex = strHex.substring(2);
            }
            strHex = strHex.substring(24);
            return "0x" + strHex;
        }
        return null;
    }

}
