package com.aucloud.commons.pojo.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WithdrawDTO {

    private Long accountId;
    private Integer accountType;
    private Integer currencyId;
    private Integer currencyChain;
    private BigDecimal amount;
    private String toAddress;

    private Integer emailCode;
    private Integer googleCode;

    private BigDecimal fee;
    private String tradeNo;
    private Long assetsId;

}
