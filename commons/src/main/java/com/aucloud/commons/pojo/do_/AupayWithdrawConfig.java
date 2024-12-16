package com.aucloud.commons.pojo.do_;

import java.io.Serializable;
import java.math.BigDecimal;

public class AupayWithdrawConfig extends AupayWithdrawConfigKey implements Serializable {

    private BigDecimal triggerAmount;

    private BigDecimal supplementAmount;

    private BigDecimal withdrawFee;

    private BigDecimal minWithdrawAmount;

    private BigDecimal maxWithdrawAmount;

    private BigDecimal transferFee;

    private String address;

    private static final long serialVersionUID = 1L;

    public BigDecimal getTriggerAmount() {
        return triggerAmount;
    }

    public void setTriggerAmount(BigDecimal triggerAmount) {
        this.triggerAmount = triggerAmount;
    }

    public BigDecimal getSupplementAmount() {
        return supplementAmount;
    }

    public void setSupplementAmount(BigDecimal supplementAmount) {
        this.supplementAmount = supplementAmount;
    }

    public BigDecimal getWithdrawFee() {
        return withdrawFee;
    }

    public void setWithdrawFee(BigDecimal withdrawFee) {
        this.withdrawFee = withdrawFee;
    }

    public BigDecimal getMinWithdrawAmount() {
        return minWithdrawAmount;
    }

    public void setMinWithdrawAmount(BigDecimal minWithdrawAmount) {
        this.minWithdrawAmount = minWithdrawAmount;
    }

    public BigDecimal getMaxWithdrawAmount() {
        return maxWithdrawAmount;
    }

    public void setMaxWithdrawAmount(BigDecimal maxWithdrawAmount) {
        this.maxWithdrawAmount = maxWithdrawAmount;
    }

    public BigDecimal getTransferFee() {
        return transferFee;
    }

    public void setTransferFee(BigDecimal transferFee) {
        this.transferFee = transferFee;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}