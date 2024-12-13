package com.aucloud.eth.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.aucloud.commons.constant.CurrencyEnum;
import com.aucloud.commons.constant.QueueConstant;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class UserToTransferService {
    @Autowired
    private AupayWalletManagerService aupayWalletManagerService;

    @RabbitListener(queuesToDeclare = {@Queue(value = QueueConstant.USER_TO_TRANSFER + "eth", durable = "true", autoDelete = "false")})
    public void user2collect(String message) {
        JSONObject jsonObject = JSON.parseObject(message);
        String tradeNo = jsonObject.getString("tradeNo");
        Integer currencyId = jsonObject.getInteger("currencyId");
        BigDecimal balanceLimit = jsonObject.getBigDecimal("balanceLimit");

        String txHash = "";
        CurrencyEnum currencyEnum = CurrencyEnum.findById(currencyId);
        if (currencyEnum != null) {
            try {
                txHash = aupayWalletManagerService.user2collect(currencyEnum, balanceLimit);
            } catch (Exception e) {
                log.error("", e);
            }
        }

        JSONObject object = new JSONObject();
        object.put("tradeNo", tradeNo);
        object.put("status", StringUtils.isNotBlank(txHash));
        object.put("txHash", txHash);
        String jsonString = object.toJSONString();

    }
}
