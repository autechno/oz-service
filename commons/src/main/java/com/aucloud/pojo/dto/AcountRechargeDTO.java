package com.aucloud.pojo.dto;

import com.aucloud.constant.CurrencyEnum;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AcountRechargeDTO {

    private Integer accountId;
    private Integer accountType;
    private String tradeNo;
    private CurrencyEnum currencyEnum;
    private BigDecimal amount;
}
