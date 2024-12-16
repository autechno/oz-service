package com.aucloud.commons.pojo.do_;

import java.io.Serializable;

public class AupayWithdrawConfigKey implements Serializable {
    private Integer currencyId;

    private Integer currencyChain;

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
}