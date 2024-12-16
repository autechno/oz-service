package com.aucloud.commons.pojo.do_;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AupaySupplementWithdrawWalletRecord implements Serializable {
    private String id;

    private Date createTime;

    private Integer currencyId;

    private Integer currencyChain;

    private String fromWalletId;

    private String toWalletId;

    private BigDecimal amount;

    private String transferRecordId;

    private Integer state;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

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

    public String getFromWalletId() {
        return fromWalletId;
    }

    public void setFromWalletId(String fromWalletId) {
        this.fromWalletId = fromWalletId;
    }

    public String getToWalletId() {
        return toWalletId;
    }

    public void setToWalletId(String toWalletId) {
        this.toWalletId = toWalletId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTransferRecordId() {
        return transferRecordId;
    }

    public void setTransferRecordId(String transferRecordId) {
        this.transferRecordId = transferRecordId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}