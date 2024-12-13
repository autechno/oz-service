package com.aucloud.eth.schedule;//package com.aucloud.aupay.schedule;


import com.aucloud.commons.constant.QueueConstant;
import com.aucloud.eth.service.Web3jClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@RefreshScope
@Component
public class BlockNumberPusher {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private Web3jClientService web3jClientService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    //测试网
    //private static Web3j web3j = Web3j.build(new HttpService("https://sepolia.infura.io/v3/ce70bbe041ad437e9b44bf933ec3a146"));
    //主网
//    private static Web3j web3j;// = Web3j.build(new HttpService("https://eth-mainnet.g.alchemy.com/v2/Ijgt71weN_e9NtEiWZy1lGv6VD4bJZeY"));

    //定时扫描节点区块，从上次扫描的区块号开始，获取当前最新区块号
    @Scheduled(fixedRate = 60000L)//1分钟
    public void monitorTronTransaction() {
        try {
            Object o = redisTemplate.opsForValue().get("lastEthBlockNumber");
            long lastScanBlock = 0;
            if (o != null) {
                lastScanBlock = Integer.parseInt(o.toString());
            }
            lastScanBlock += 1;
            long blockCount = 0;
            try {
                blockCount = web3jClientService.getWeb3j().ethBlockNumber().send().getBlockNumber().longValue();
                log.debug("web3j-blockCount:[{}]", blockCount);
            } catch (IOException e) {
                log.error("web3j-blockCount:[{}]", blockCount, e);
            }
            blockCount -= 6;
            if (lastScanBlock <= 1) {
                lastScanBlock = blockCount - 1;
            }
            log.info("Mq-EthBlockNumber start:[{}] -- to:[{}]", lastScanBlock, blockCount);
            for (long i = lastScanBlock; i <= blockCount; i++) {
                rabbitTemplate.convertAndSend(QueueConstant.CHAIN_SCAN + "_eth", String.valueOf(i));
                //消息队列等待同步
            }
            redisTemplate.opsForValue().set("lastEthBlockNumber", blockCount);
        } catch (Exception e) {
            log.error("获取区块异常", e);
        }
    }

//    public static void main(String[] args) throws IOException {
//        long blockCount = web3j.ethBlockNumber().send().getBlockNumber().longValue();
//        System.out.println(blockCount);
//    }

}
