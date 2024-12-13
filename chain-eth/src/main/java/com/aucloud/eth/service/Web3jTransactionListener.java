//package com.aucloud.eth.service;
//
//import com.alibaba.fastjson2.JSON;
//import io.reactivex.disposables.Disposable;
//import io.reactivex.schedulers.Schedulers;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//import org.web3j.protocol.Web3j;
//import org.web3j.protocol.core.methods.response.Transaction;
//import org.web3j.protocol.websocket.WebSocketService;
//
//@Slf4j
//@RefreshScope
//@Service
//public class Web3jTransactionListener {
//    @Autowired
//    private TransactionService transactionService;
//
//    private String webSocketUrl;//"wss://sepolia.infura.io/ws/v3/ce70bbe041ad437e9b44bf933ec3a146";
//    private WebSocketService webSocketService;
//    private Web3j web3j;
//    private Disposable subscribe;
//
//    @Value("${address.web3j.ws}")
//    public void setListener(String webSocketUrl) {
//        log.info("start web3j transaction listener. webSocketUrl:{}", webSocketUrl);
//        try {
//            try {
//                if (subscribe != null) {
//                    subscribe.dispose();
//                }
//                if (web3j != null) {
//                    web3j.shutdown();
//                }
//                if (webSocketService != null) {
//                    webSocketService.close();
//                }
//            } catch (Exception e) {
//                log.error("关闭监听链接异常:{}", e.getMessage());
//            }
//            this.webSocketUrl = webSocketUrl;
//            webSocketService = new WebSocketService(webSocketUrl, false);
////            webSocketService.connect();
//            webSocketService.connect(msg -> {
////                log.info("web3j websocket service connect onMassage:{}", msg);
//            },throwable -> {
//                log.error("web3j websocket service connect onThrowable:", throwable);
//                try {
//                    Thread.sleep(10000L);
//                } catch (InterruptedException e) {
//                    log.error("InterruptedException:", e);
//                }
//                setListener(this.webSocketUrl);
//            },()->{
//                log.error("web3j websocket service connect onClose:{}", webSocketUrl);
//                try {
//                    Thread.sleep(10000L);
//                } catch (InterruptedException e) {
//                    log.error("InterruptedException:", e);
//                }
//                setListener(this.webSocketUrl);
//            });
//            web3j = Web3j.build(webSocketService);
////            subscribe = web3j.transactionFlowable().subscribe(this::dealTransaction);
//
//            subscribe = web3j.transactionFlowable().subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).subscribe(this::dealTransaction, throwable -> log.error("subscribe error", throwable));
//        } catch (Exception e) {
//            log.error("web3j transaction listener error", e);
//        }
//    }
//
//    @Scheduled(initialDelay = 60000L,fixedRate = 10000L)
//    public void keepListenerAlive() {
//        if (subscribe.isDisposed()) {
//            log.error("web3j websocket service is disposed");
//            setListener(this.webSocketUrl);
//        } else {
//            log.debug("web3j websocket listener is alive");
//        }
//    }
//
////    @PostConstruct
////    public void testListener() {
////        new Thread(()->{
////            while (true) {
////                if (subscribe.isDisposed()) {
////                    setListener(webSocketUrl);
////                }
////            }
////        }).start();
////    }
////    public void fn(String contractAddress) {
////        // 设置要监听的合约地址
//////        String contractAddress = "0xYourContractAddress"; // 替换为你的合约地址
////        // 设置Transfer事件的ABI
////        Event transferEvent = new Event("Transfer",
////                Arrays.asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {})
////        );
////        String encodedEventSignature = EventEncoder.encode(transferEvent);
////
////        // 创建过滤器
////        EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, contractAddress);
////        filter.addSingleTopic(encodedEventSignature);
////        // 订阅事件
////        Disposable subscription = web3j.ethLogFlowable(filter).subscribe(log -> {
////            String transactionHash = log.getTransactionHash();
////        });
////    }
//
//    private void dealTransaction(Transaction transaction) {
//        try {
//            transactionService.pushTransaction(transaction);
//        } catch (Exception e) {
//            log.error("deal transaction error, transaction:{}", JSON.toJSONString(transaction), e);
//        }
//    }
//
//}
