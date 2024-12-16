package com.aucloud.commons.pojo.do_;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AupayAssetsDailyStatistics implements Serializable {

    private Date date;

    private BigDecimal usdtFlowIn;

    private BigDecimal usdtFlowOut;

    private BigDecimal assetsOfUsdtAmount;

    private static final long serialVersionUID = 1L;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getUsdtFlowIn() {
        return usdtFlowIn;
    }

    public void setUsdtFlowIn(BigDecimal usdtFlowIn) {
        this.usdtFlowIn = usdtFlowIn;
    }

    public BigDecimal getUsdtFlowOut() {
        return usdtFlowOut;
    }

    public void setUsdtFlowOut(BigDecimal usdtFlowOut) {
        this.usdtFlowOut = usdtFlowOut;
    }

    public BigDecimal getAssetsOfUsdtAmount() {
        return assetsOfUsdtAmount;
    }

    public void setAssetsOfUsdtAmount(BigDecimal assetsOfUsdtAmount) {
        this.assetsOfUsdtAmount = assetsOfUsdtAmount;
    }
}