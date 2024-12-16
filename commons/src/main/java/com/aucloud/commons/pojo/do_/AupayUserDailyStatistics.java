package com.aucloud.commons.pojo.do_;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AupayUserDailyStatistics implements Serializable {
    private Date date;

    private Integer regNum;

    private Integer activeNum;

    private Integer fastPayNum;

    private BigDecimal fastPayAmount;

    private static final long serialVersionUID = 1L;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getRegNum() {
        return regNum;
    }

    public void setRegNum(Integer regNum) {
        this.regNum = regNum;
    }

    public Integer getActiveNum() {
        return activeNum;
    }

    public void setActiveNum(Integer activeNum) {
        this.activeNum = activeNum;
    }

    public Integer getFastPayNum() {
        return fastPayNum;
    }

    public void setFastPayNum(Integer fastPayNum) {
        this.fastPayNum = fastPayNum;
    }

    public BigDecimal getFastPayAmount() {
        return fastPayAmount;
    }

    public void setFastPayAmount(BigDecimal fastPayAmount) {
        this.fastPayAmount = fastPayAmount;
    }
}