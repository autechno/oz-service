package com.aucloud.pojo.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FastSwapDTO {

    private Integer outCurrencyId;
    private Integer outChain;

    private Integer inCurrencyId;
    private Integer inChain;

    private BigDecimal transOutAmount;
    private BigDecimal transInAmount;
    private BigDecimal feeAmount;
}
