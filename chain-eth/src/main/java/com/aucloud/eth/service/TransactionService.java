package com.aucloud.eth.service;

import com.alibaba.fastjson2.JSON;
import com.aucloud.commons.constant.CurrencyEnum;
import com.aucloud.commons.constant.QueueConstant;
import com.aucloud.commons.constant.RedisCacheKeys;
import com.aucloud.commons.pojo.dto.RechargeDTO;
import com.aucloud.commons.utils.Tools;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TransactionService {

    @Autowired
    private ContractProperties contractProperties;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private Web3jClientService web3jClientService;

    public RechargeDTO parseTransaction(Transaction transaction) throws IOException {
        RechargeDTO rechargeDTO = null;
        String to = transaction.getTo();
        if (StringUtils.isBlank(to)) {
            return null;
        }
        String txId = transaction.getHash();
        BigInteger value = transaction.getValue();
        String from = transaction.getFrom();
        BigInteger blockNumber = transaction.getBlockNumber();
        BigDecimal ethAmount = Convert.fromWei(new BigDecimal(value), Convert.Unit.ETHER);
        Optional<TransactionReceipt> optionalTransactionReceipt;
        Object walletId = redisTemplate.opsForHash().get(RedisCacheKeys.SCAN_WALLET_ADDRESS+"-"+ CurrencyEnum.CurrencyChainEnum.ETH.id+"-"+CurrencyEnum.ETH.id, to);
        String[] result = null;
        if (walletId != null || (result = checkAddressType(to))!=null) {
            //to address 是本系统地址，或者 是本合约地址 的
            optionalTransactionReceipt = web3jClientService.getWeb3j().ethGetTransactionReceipt(txId).send().getTransactionReceipt();
            TransactionReceipt transactionReceipt = optionalTransactionReceipt.orElse(null);
            if(!checkTransactionState(transactionReceipt)) {
                log.error("check transaction state failed. txId: {}", txId);
                return null;
            }
            if (walletId != null) {
                //eth交易
                //有关系则进行eth到账处理
                rechargeDTO = new RechargeDTO();
                rechargeDTO.setWalletId(walletId.toString());
                rechargeDTO.setAmount(ethAmount);
                rechargeDTO.setFromAddress(from);
                rechargeDTO.setToAddress(to);
                rechargeDTO.setTxId(txId);
                rechargeDTO.setCurrencyId(CurrencyEnum.ETH);
                rechargeDTO.setCurrencyChain(CurrencyEnum.CurrencyChainEnum.ETH);
                rechargeDTO.setBlockNumber(blockNumber.intValue());
            } else {
                //合约交易
                List<Log> logs = transactionReceipt.getLogs();
                CurrencyEnum c = CurrencyEnum.findById(Integer.parseInt(result[0]));
                assert c != null;
                String fromAddress = null;
                String toAddress = null;
                BigDecimal amount = null;
                boolean isTransfer = false;
                for (Log log : logs) {
                    List<String> topics = log.getTopics();
                    String event = topics.get(0);
                    if(event.equalsIgnoreCase(contractProperties.getTransfer())){//是否transfer交易
                        isTransfer = true;
                        fromAddress = topics.get(1);
                        toAddress = Tools.hexToAddress(topics.get(2));
                        String address = log.getAddress();
                        String v = log.getData();
                        if (address.equalsIgnoreCase(result[1])) {
                            amount = new BigDecimal(new BigInteger(v.substring(2), 16)).divide(
                                    BigDecimal.TEN.pow(c.precision) ,
                                    c.precision,
                                    RoundingMode.HALF_EVEN);
                        }
                        break;
                    }
                }
                if( isTransfer ) {
                    //查看接收地址和我们有没有关系
                    assert toAddress != null;
                    walletId = redisTemplate.opsForHash().get(RedisCacheKeys.SCAN_WALLET_ADDRESS+"-"+CurrencyEnum.CurrencyChainEnum.ETH.id+"-"+c.id, toAddress);
                    log.info("is transfer transaction. txId: {} from: {}; toAddress: {} 对应本地钱包id: {}", txId, fromAddress, toAddress, walletId);
                    if(walletId!=null) {
                        //有关系则进行到账处理
                        rechargeDTO = new RechargeDTO();
                        rechargeDTO.setWalletId(walletId.toString());
                        rechargeDTO.setAmount(amount);
                        rechargeDTO.setFromAddress(from);
                        rechargeDTO.setToAddress(toAddress);
                        rechargeDTO.setTxId(txId);
                        rechargeDTO.setCurrencyId(c);
                        rechargeDTO.setCurrencyChain(CurrencyEnum.CurrencyChainEnum.ETH);
                        rechargeDTO.setBlockNumber(blockNumber.intValue());
                    }
                }
            }
        }
        return rechargeDTO;
    }

    public String[] checkAddressType(String toAddress) {
        log.debug("checkAddressType: {}", toAddress);
        String[] result = new String[2];
        if(toAddress.equalsIgnoreCase(contractProperties.getUsdt())) {
            result[0] = CurrencyEnum.USDT.id.toString();
            result[1] = contractProperties.getUsdt();
            return result;
        }
        if(toAddress.equalsIgnoreCase(contractProperties.getOzc()))  {
            result[0] = CurrencyEnum.OZC.id.toString();
            result[1] = contractProperties.getOzc();
            return result;
        }
        if(toAddress.equalsIgnoreCase(contractProperties.getToto())) {
            result[0] = CurrencyEnum.TOTO.id.toString();
            result[1] = contractProperties.getToto();
            return result;
        }
        return null;
    }

    public boolean checkTransactionState(TransactionReceipt transactionReceipt) {
        return transactionReceipt != null && transactionReceipt.isStatusOK();
    }

    public void pushTransaction(Transaction transaction) throws Exception {
        RechargeDTO rechargeDTO = parseTransaction(transaction);
        if (rechargeDTO != null) {
            String jsonString = JSON.toJSONString(rechargeDTO);
            rabbitTemplate.convertAndSend(QueueConstant.RECHARGE_DEAL, jsonString);
            log.info("推送 toaddress:{} 本地钱包:{} {}交易 到 MQ 425, msg:{}", rechargeDTO.getToAddress(), rechargeDTO.getWalletId(), rechargeDTO.getCurrencyId(), jsonString);
        }
    }

}
