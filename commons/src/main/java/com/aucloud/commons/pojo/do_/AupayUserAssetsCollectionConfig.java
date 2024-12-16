package com.aucloud.commons.pojo.do_;

import java.io.Serializable;
import java.math.BigDecimal;

public class AupayUserAssetsCollectionConfig implements Serializable {
    private Integer id;

    private BigDecimal anytimeAutoTriggerAmount;

    private Integer weekAutoDayNumOfWeek;

    private Integer weekAutoHourNumOfDay;

    private BigDecimal weekAutoTriggerAmount;

    private BigDecimal manualAmountLimit;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getAnytimeAutoTriggerAmount() {
        return anytimeAutoTriggerAmount;
    }

    public void setAnytimeAutoTriggerAmount(BigDecimal anytimeAutoTriggerAmount) {
        this.anytimeAutoTriggerAmount = anytimeAutoTriggerAmount;
    }

    public Integer getWeekAutoDayNumOfWeek() {
        return weekAutoDayNumOfWeek;
    }

    public void setWeekAutoDayNumOfWeek(Integer weekAutoDayNumOfWeek) {
        this.weekAutoDayNumOfWeek = weekAutoDayNumOfWeek;
    }

    public Integer getWeekAutoHourNumOfDay() {
        return weekAutoHourNumOfDay;
    }

    public void setWeekAutoHourNumOfDay(Integer weekAutoHourNumOfDay) {
        this.weekAutoHourNumOfDay = weekAutoHourNumOfDay;
    }

    public BigDecimal getWeekAutoTriggerAmount() {
        return weekAutoTriggerAmount;
    }

    public void setWeekAutoTriggerAmount(BigDecimal weekAutoTriggerAmount) {
        this.weekAutoTriggerAmount = weekAutoTriggerAmount;
    }

    public BigDecimal getManualAmountLimit() {
        return manualAmountLimit;
    }

    public void setManualAmountLimit(BigDecimal manualAmountLimit) {
        this.manualAmountLimit = manualAmountLimit;
    }
}