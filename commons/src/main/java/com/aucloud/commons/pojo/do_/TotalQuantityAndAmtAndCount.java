package com.aucloud.commons.pojo.do_;

import java.io.Serializable;
import java.math.BigDecimal;

public class TotalQuantityAndAmtAndCount implements Serializable {
    private static final long serialVersionUID = 1L;
    private String tranDate;
    private Integer totalCount;
    private BigDecimal totalQuantity;
    private BigDecimal totalAmount;

    public String getTranDate() {
        return tranDate;
    }

    public void setTranDate(String tranDate) {
        this.tranDate = tranDate;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public BigDecimal getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(BigDecimal totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
