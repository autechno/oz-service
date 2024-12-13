package com.aucloud.eth.service;

import com.aucloud.commons.constant.QueueConstant;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.core.methods.response.Transaction;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ScanTransactionService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private Web3jClientService web3jClientService;

    /**
     * 扫描ERC20链上交易
     * @param message 区块号
     */
    @RabbitListener(queuesToDeclare = {@Queue(value = QueueConstant.CHAIN_SCAN + "_eth", durable = "true", autoDelete = "false")})
    public void scanERC20(String message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) {
        HashOperations<String,String,String> hashOperations = redisTemplate.opsForHash();
        BigInteger bigInteger = new BigInteger(message);
        DefaultBlockParameter defaultBlockParameter = DefaultBlockParameter.valueOf(bigInteger);
        List<EthBlock.TransactionResult> transactions = null;//区块内的所有交易记录
        try {
            transactions = web3jClientService.getWeb3j().ethGetBlockByNumber(defaultBlockParameter,true).send().getBlock().getTransactions();
        } catch (IOException e) {
            log.error("查询区块内交易记录异常 block:{}",message, e);
            return;
        }
        log.debug("start scanERC20. block:[{}],tranNum:[{}]",message,transactions.size());
        for(EthBlock.TransactionResult transactionResult : transactions) {
            EthBlock.TransactionObject transactionObject = (EthBlock.TransactionObject) transactionResult.get();
            String hash = transactionObject.getHash();
            String from = transactionObject.getFrom();
            String to = transactionObject.getTo();
            //查看接收地址和我们有没有关系
            if(to == null) {
                continue;
            }
            log.debug("block:[{}],hash:[{}],fromAddress:[{}],toAddress:[{}]",message,hash,from,to);
            try {
                transactionService.pushTransaction(transactionObject.get());
            } catch (Exception e) {
                log.error("解析transaction异常, hash:{}", hash, e);
            }
        }
        log.debug("end scanERC20 block:[{}],tranNum:[{}],",message,transactions.size());
    }

    public void pushTranscation(String txId) {
        try {
            Request<?, EthTransaction> ethTransactionRequest = web3jClientService.getWeb3j().ethGetTransactionByHash(txId);
            Optional<Transaction> transaction = ethTransactionRequest.send().getTransaction();
            transactionService.pushTransaction(transaction.get());
        } catch (Exception e) {
            log.error("解析transaction异常, hash:{}", txId, e);
        }
    }
}
