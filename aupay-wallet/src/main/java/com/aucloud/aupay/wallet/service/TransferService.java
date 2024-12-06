package com.aucloud.aupay.wallet.service;

import com.alibaba.fastjson.JSON;
import com.aucloud.aupay.constant.QueueConstant;
import com.aucloud.aupay.constant.RedisCacheKeys;
import com.aucloud.aupay.constant.WalletTransferRecordState;
import com.aucloud.aupay.constant.WalletTransferTradeType;
import com.aucloud.aupay.dao.WalletDao;
import com.aucloud.aupay.entity.TransferRequest;
import com.aucloud.aupay.pojo.bo.AupayDigitalCurrencyWallet;
import com.aucloud.aupay.pojo.do_.AupayWalletTransferRecord;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class TransferService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private GasWalletService gasWalletService;
    @Autowired
    private WalletDao walletDao;
    @Autowired
    private ChainTransferDealService chainTransferDealService;
    @Autowired
    private WalletService walletService;
    @Autowired
    private UserWalletCollectionService userWalletCollectionService;
    @Autowired
    private WithDrawWalletService withDrawWalletService;
    @Autowired
    private TransferWalletService transferWalletService;

    /**
     * 后续修改为此处签发事务
     * 节点自行进行处理  防止出现重复调用transfer
     */
    @RabbitListener(queuesToDeclare = {@Queue(value = QueueConstant.TRANSFER_DEAL, durable = "true", autoDelete = "false")})
    public void transferDeal(String message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) {
        log.info("listen queues-transer_deal:[{}]",message);
        TransferRequest transferRequest = JSON.parseObject(message, TransferRequest.class);
        try {
            transfer(transferRequest);
        } catch (Exception e) {
            log.error("transfer error, businessId: {}", transferRequest.getBusinessId(), e);
            ifFail(transferRequest.getBusinessId(),transferRequest.getTradeType());
        }
    }

    public void transfer(TransferRequest transferRequest) {
        if (ifCheckGas(transferRequest)) {
            log.info("需要执行校验gas的交易");
            checkGas(transferRequest);
        } else {
            log.info("不需呀检查gas费，提币暂不校验？");
            sendTransfer(transferRequest);
        }
    }

    public boolean ifCheckGas(TransferRequest transferRequest) {
        Integer tradeType = transferRequest.getTradeType();
        if (tradeType == null) {
            return false;
        }
        switch (tradeType) {
            case WalletTransferTradeType.TRANSFER_FEE_SUPPLEMENT://转gas费的交易不校验gas费
            case WalletTransferTradeType.WITHDRAW://提币不校验gas费
                return false;
            default:
                return true;
        }
//        return !transferRequest.getTradeType().equals(WalletTransferTradeType.WITHDRAW);//提币不校验gas费
    }

    public void checkGas(TransferRequest transferRequest) {
        TransferRequest gas = ifSendGas(transferRequest);
        if (gas != null) {
            log.info("需要补充gas费，下一步存入redis,transferRequest:[{}]",JSON.toJSONString(transferRequest));
            //redis.set transRequest
            redisTemplate.opsForValue().set(RedisCacheKeys.TRANSFER_REQUEST_FOR_NEXT+gas.getBusinessId(), JSON.toJSONString(transferRequest), 10, TimeUnit.MINUTES);
            sendTransfer(gas);//send gas transferRequest
        } else {
            log.info("不需要补充gas费，直接交易.");
            sendTransfer(transferRequest);
        }
    }
    public TransferRequest ifSendGas(TransferRequest transferRequest) {
        String fromWalletId = transferRequest.getWalletId().toString();
        AupayDigitalCurrencyWallet wallet = walletDao.getWalletById(fromWalletId);
        BigDecimal supplementAmount = gasWalletService.ifSendGasAndGetSupplementAmount(wallet);
        if (supplementAmount.compareTo(BigDecimal.ZERO) > 0) {
            return gasWalletService.supplementTransferFee(wallet, supplementAmount, transferRequest.getBusinessId());
        }
        return null;
    }

//    public boolean ifSupplyBalance(TransferRequest transferRequest) {
//        Integer tradeType = transferRequest.getTradeType();
//        if (WalletTransferTradeType.WITHDRAW == tradeType) {
//            //提币钱包补充余额
//        }
//        return false;
//    }

    public void sendTransfer(TransferRequest transferRequest) {
        log.info("去调用链上交易，transferRequest:[{}]",JSON.toJSONString(transferRequest));
        try {
            chainTransferDealService.transfer(transferRequest);
        } catch (Exception e) {
            log.error("chain transfer error", e);
            ifFail(transferRequest.getBusinessId(), transferRequest.getTradeType());
        }
    }

    /**异步的结果，监听定时任务查询交易返回后 调用**/
    @Transactional
    public void sendResult(AupayWalletTransferRecord aupayWalletTransferRecord) {
        walletService.updateAupayWalletTransferRecord(aupayWalletTransferRecord);
        Integer state = aupayWalletTransferRecord.getState();

        if (WalletTransferRecordState.FAILED.equals(state)) {
            log.info("回调，链上交易失败，执行失败更新");
            String businessId = aupayWalletTransferRecord.getBusinessId();
            Integer tradeType = aupayWalletTransferRecord.getTradeType();
            ifFail(businessId, tradeType);
        } else if (WalletTransferRecordState.SUCCESS.equals(state)) {
            log.info("回调，链上交易成功，执行成功更新");
            ifSuccess(aupayWalletTransferRecord);
        }
    }
    public void ifFail(String businessId, Integer tradeType) {
        try {
            if (tradeType.equals(WalletTransferTradeType.WITHDRAW)) {
                //提币  提币钱包->用户提币地址
                withDrawWalletService.withDrawFail(businessId);
            } else if (tradeType.equals(WalletTransferTradeType.USER_ASSETS_COLLECTION)) {
                //用户钱包--归集，用户钱包->中转钱包
                userWalletCollectionService.userAssetsCollectTransferFail(businessId);
            } else if (tradeType.equals(WalletTransferTradeType.WITHDRAW_SUPPLEMENT)) {
                //提币钱包--补充币，中转钱包->提币钱包
                withDrawWalletService.withdrawSupplementTransferFail(businessId);
            } else if (tradeType.equals(WalletTransferTradeType.TRANSFER_WALLET_RESERVE)) {
                //中转钱包--到储备钱包，中转钱包->储备钱包
                transferWalletService.transferWalletReserveFail(businessId);
            } else if (tradeType.equals(WalletTransferTradeType.TRANSFER_FEE_SUPPLEMENT)) {
                //gas费补充
                gasWalletService.transferFeeSupplementFail(businessId);
                TransferRequest gasNextTransfer = getGasNextTransfer(businessId);
                if (gasNextTransfer != null) {
                    String _businessId = gasNextTransfer.getBusinessId();
                    Integer _tradeType = gasNextTransfer.getTradeType();
                    ifFail(_businessId, _tradeType);//源操作也同样失败处理
                }
    //        } else if (tradeType.equals(WalletTransferTradeType.USER_ASSETS_COLLECTION_SUPPLEMENT_FEE)) {
    //            //用户钱包--gas费充值
    //            userWalletCollectionService.userAssetsCollectFeeTransferFail(businessId);
            }
        } catch (Exception e) {
            log.error("更新失败结果异常", e);
            throw e;
        }
    }
    public void ifSuccess(AupayWalletTransferRecord aupayWalletTransferRecord) {
        try {
            Integer tradeType = aupayWalletTransferRecord.getTradeType();
            if (tradeType.equals(WalletTransferTradeType.WITHDRAW)) {
                //提币  提币钱包->用户提币地址
                withDrawWalletService.withdrawComplete(aupayWalletTransferRecord);
            } else if (tradeType.equals(WalletTransferTradeType.USER_ASSETS_COLLECTION)) {
                //用户钱包--归集，用户钱包->中转钱包
                userWalletCollectionService.userAssetsCollectTransferComplete(aupayWalletTransferRecord);
            } else if (tradeType.equals(WalletTransferTradeType.WITHDRAW_SUPPLEMENT)) {
                //提币钱包--补充币，中转钱包->提币钱包
                withDrawWalletService.withdrawSupplementTransferComplete(aupayWalletTransferRecord);

            } else if (tradeType.equals(WalletTransferTradeType.TRANSFER_WALLET_RESERVE)) {
                //中转钱包--到储备钱包，中转钱包->储备钱包
                transferWalletService.transferWalletReserveComplete(aupayWalletTransferRecord);
            } else if (tradeType.equals(WalletTransferTradeType.TRANSFER_FEE_SUPPLEMENT)) {
                //gas费补充
                gasWalletService.transferFeeSupplementComplete(aupayWalletTransferRecord);
                TransferRequest gasNext = getGasNextTransfer(aupayWalletTransferRecord.getBusinessId());
                if (gasNext != null) {
                    sendTransfer(gasNext);
                }
    //        } else if (tradeType.equals(WalletTransferTradeType.USER_ASSETS_COLLECTION_SUPPLEMENT_FEE)) {
    //            //用户钱包--gas费充值
    //            userWalletCollectionService.userAssetsCollectFeeTransferComplete(aupayWalletTransferRecord.getBusinessId());
            }
        } catch (Exception e) {
            log.error("更新成功结果异常", e);
            throw e;
        }
    }

    /**redis get transferRequest**/
    public TransferRequest getGasNextTransfer(String businessId) {
        Object o = redisTemplate.opsForValue().get(RedisCacheKeys.TRANSFER_REQUEST_FOR_NEXT + businessId);
        if (o != null) {
            return JSON.parseObject(o.toString(), TransferRequest.class);
        }
        return null;
    }
}
