package com.aucloud.pojo.dto;

import com.aucloud.constant.CurrencyEnum;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

@Data
public class WithdrawBatchDto implements Serializable {

    private String batchNo;
    private CurrencyEnum currencyEnum;
    private Map<String, BigDecimal> addressAmounts;
}
