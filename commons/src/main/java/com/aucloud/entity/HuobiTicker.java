package com.aucloud.entity;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

@Data
public class HuobiTicker {

    private String status;
    private String symbol;
    private BigDecimal low;
    private BigDecimal high;
    private BigDecimal bid;
    private BigDecimal ask;
    private BigDecimal point;
    private BigDecimal change;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
