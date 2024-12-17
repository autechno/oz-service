package com.aucloud.commons.pojo.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class FastSwapRecordQuery {

    private Date startTime;
    private Date endTime;
    private Integer cashOutCurrencyId;
    private Integer cashOutCurrencyChain;
    private Integer cashInCurrencyId;
    private Integer cashInCurrencyChain;
    private BigDecimal cashOutAmount;
    private BigDecimal cashInAmount;
}
