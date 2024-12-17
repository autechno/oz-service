package com.aucloud.commons.pojo.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FastSwapDTO {

    private Long accountId;
    private Integer accountType;
    private Long employeeId;

    private Integer outCurrencyId;
    private Integer outChain;

    private Integer inCurrencyId;
    private Integer inChain;

    private BigDecimal transOutAmount;
    private BigDecimal transInAmount;
    private BigDecimal feeAmount;
}
