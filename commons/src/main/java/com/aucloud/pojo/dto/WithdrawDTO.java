package com.aucloud.pojo.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WithdrawDTO {

    private Integer accountId;
    private Integer accountType;
    private Integer currencyId;
    private Integer currencyChain;
    private BigDecimal amount;
    private String toAddress;

    private Integer emailCode;
    private Integer googleCode;

    private BigDecimal fee;
    private String tradeNo;

}
