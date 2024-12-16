package com.aucloud.commons.pojo.do_;

import java.io.Serializable;

public class AupayWithdrawWalletConfig implements Serializable {
    private Integer currencyId;

    private Integer currencyChain;

    private String walletId;

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
}