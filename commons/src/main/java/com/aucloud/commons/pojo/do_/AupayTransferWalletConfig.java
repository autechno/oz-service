package com.aucloud.commons.pojo.do_;

import java.io.Serializable;
import java.math.BigDecimal;

public class AupayTransferWalletConfig implements Serializable {

    private Integer currencyId;

    private Integer currencyChain;

    private String walletId;

    private String address;

    private BigDecimal autoReserveTriggerAmount;

    private BigDecimal reserveAmount;

    private BigDecimal fastPaymentSettleAmount;

    private static final long serialVersionUID = 1L;

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }

    public Integer getCurrencyChain() {
        return currencyChain;
    }

    public void setCurrencyChain(Integer currencyChain) {
        this.currencyChain = currencyChain;
    }

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public BigDecimal getAutoReserveTriggerAmount() {
        return autoReserveTriggerAmount;
    }

    public void setAutoReserveTriggerAmount(BigDecimal autoReserveTriggerAmount) {
        this.autoReserveTriggerAmount = autoReserveTriggerAmount;
    }

    public BigDecimal getReserveAmount() {
        return reserveAmount;
    }

    public void setReserveAmount(BigDecimal reserveAmount) {
        this.reserveAmount = reserveAmount;
    }

    public BigDecimal getFastPaymentSettleAmount() {
        return fastPaymentSettleAmount;
    }

    public void setFastPaymentSettleAmount(BigDecimal fastPaymentSettleAmount) {
        this.fastPaymentSettleAmount = fastPaymentSettleAmount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}