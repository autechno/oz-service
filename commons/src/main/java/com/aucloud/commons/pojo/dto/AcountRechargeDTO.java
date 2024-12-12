package com.aucloud.commons.pojo.dto;

import com.aucloud.commons.constant.CurrencyEnum;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AcountRechargeDTO {

    private Long accountId;
    private Integer accountType;
    private String tradeNo;
    private CurrencyEnum currencyEnum;
    private BigDecimal amount;
}
