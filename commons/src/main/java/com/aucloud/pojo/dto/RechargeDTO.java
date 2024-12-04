package com.aucloud.pojo.dto;

import com.aucloud.constant.CurrencyEnum;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RechargeDTO {

    private Integer blockNumber;
    private String fromAddress;
    private String toAddress;
    private String walletId;
    private CurrencyEnum currencyId;
    private CurrencyEnum.CurrencyChainEnum currencyChain;
    private BigDecimal amount;
    private Integer state;
    private String txId;

}
