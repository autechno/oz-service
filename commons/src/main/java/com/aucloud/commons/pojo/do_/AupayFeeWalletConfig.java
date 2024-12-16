package com.aucloud.commons.pojo.do_;

import java.io.Serializable;
import java.math.BigDecimal;

public class AupayFeeWalletConfig extends AupayFeeWalletConfigKey implements Serializable {

    private String walletId;

    private BigDecimal triggerAmount;

    private BigDecimal supplementAmount;

    private BigDecimal singleTransferFee;

    private static final long serialVersionUID = 1L;

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

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

    public BigDecimal getSingleTransferFee() {
        return singleTransferFee;
    }

    public void setSingleTransferFee(BigDecimal singleTransferFee) {
        this.singleTransferFee = singleTransferFee;
    }
}