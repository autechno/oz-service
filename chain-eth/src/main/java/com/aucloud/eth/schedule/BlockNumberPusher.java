package com.aucloud.eth.schedule;//package com.aucloud.aupay.schedule;
//
//import com.aucloud.aupay.constant.QueueConstant;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.web3j.protocol.Web3j;
//import org.web3j.protocol.http.HttpService;
//
//import java.io.IOException;
//
//@Slf4j
//@RefreshScope
//@Component
//public class BlockNumberPusher {
//
//    @Autowired
//    private RedisTemplate redisTemplate;
//
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//
//    //测试网
//    //private static Web3j web3j = Web3j.build(new HttpService("https://sepolia.infura.io/v3/ce70bbe041ad437e9b44bf933ec3a146"));
//    //主网
//    private static Web3j web3j;// = Web3j.build(new HttpService("https://eth-mainnet.g.alchemy.com/v2/Ijgt71weN_e9NtEiWZy1lGv6VD4bJZeY"));
//
//    @Value("${address.web3j.url}")
//    public void setAddressWeb3jUrl(String addressWeb3jUrl) {
//        log.info("BlockNumberPusher.setAddressWeb3jUrl:{}", addressWeb3jUrl);
//        web3j = Web3j.build(new HttpService(addressWeb3jUrl));
//    }
//
//    //定时扫描节点区块，从上次扫描的区块号开始，获取当前最新区块号
//    @Scheduled(fixedRate = 30000L)//半分钟
//    public void monitorTronTransaction() {
//        try {
//            ValueOperations valueOperations = redisTemplate.opsForValue();
//            Object o = valueOperations.get("lastEthBlockNumber");
//            int lastScanBlock = 0;
//            if(o != null) {
//                lastScanBlock = Integer.parseInt(o.toString());
//            }
//            lastScanBlock += 1;
//            long blockCount = 0;
//            try {
//                blockCount = web3j.ethBlockNumber().send().getBlockNumber().longValue();
//                log.debug("web3j-blockCount:[{}]",blockCount);
//            } catch (IOException e) {
//                log.error("web3j-blockCount:[{}]",blockCount,e);
//            }
//            blockCount -= 6;
//            if(lastScanBlock <= 1) {
//                lastScanBlock = (int) (blockCount - 1);
//            }
//            log.info("Mq-EthBlockNumber start:[{}] -- to:[{}]",lastScanBlock,blockCount);
//            for(int i = lastScanBlock; i <= blockCount; i++) {
//                rabbitTemplate.convertAndSend(QueueConstant.CHAIN_SCAN + "_eth", String.valueOf(i));
//                //消息队列等待同步
//            }
//            valueOperations.set("lastEthBlockNumber",blockCount + "");
//        } catch (Exception e) {
//            log.error("获取区块异常", e);
//        }
//    }
//
//    public static void main(String[] args) throws IOException {
//        long blockCount = web3j.ethBlockNumber().send().getBlockNumber().longValue();
//        System.out.println(blockCount);
//    }
//
//
//}
