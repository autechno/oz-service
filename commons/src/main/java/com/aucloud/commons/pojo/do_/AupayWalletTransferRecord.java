package com.aucloud.commons.pojo.do_;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AupayWalletTransferRecord implements Serializable {

    private String id;

    private Date createTime;

    private Integer currencyId;

    private Integer currencyChain;

    private Integer tradeType;

    private BigDecimal fromBeforeBalance;

    private BigDecimal toBeforeBalance;

    private BigDecimal amount;

    private BigDecimal fromAfterBalance;

    private BigDecimal toAfterBalance;

    private String fromAddress;

    private String fromWalletId;

    private String fromAddressInstructions;

    private String fromUserId;

    private String toAddress;

    private String toAddressInstructions;

    private String toWalletId;

    private String toUserId;

    private String chainTxId;

    private String transactionId;

    private Date finishTime;

    private Integer state;

    private String businessId;

    private Integer blockNumber;

    private BigDecimal transferFee;

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

    public Integer getTradeType() {
        return tradeType;
    }

    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getFromBeforeBalance() {
        return fromBeforeBalance;
    }

    public void setFromBeforeBalance(BigDecimal fromBeforeBalance) {
        this.fromBeforeBalance = fromBeforeBalance;
    }

    public BigDecimal getToBeforeBalance() {
        return toBeforeBalance;
    }

    public void setToBeforeBalance(BigDecimal toBeforeBalance) {
        this.toBeforeBalance = toBeforeBalance;
    }

    public BigDecimal getFromAfterBalance() {
        return fromAfterBalance;
    }

    public void setFromAfterBalance(BigDecimal fromAfterBalance) {
        this.fromAfterBalance = fromAfterBalance;
    }

    public BigDecimal getToAfterBalance() {
        return toAfterBalance;
    }

    public void setToAfterBalance(BigDecimal toAfterBalance) {
        this.toAfterBalance = toAfterBalance;
    }

    public String getFromAddressInstructions() {
        return fromAddressInstructions;
    }

    public void setFromAddressInstructions(String fromAddressInstructions) {
        this.fromAddressInstructions = fromAddressInstructions;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getFromWalletId() {
        return fromWalletId;
    }

    public void setFromWalletId(String fromWalletId) {
        this.fromWalletId = fromWalletId;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getToAddressInstructions() {
        return toAddressInstructions;
    }

    public void setToAddressInstructions(String toAddressInstructions) {
        this.toAddressInstructions = toAddressInstructions;
    }

    public String getToWalletId() {
        return toWalletId;
    }

    public void setToWalletId(String toWalletId) {
        this.toWalletId = toWalletId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getChainTxId() {
        return chainTxId;
    }

    public void setChainTxId(String chainTxId) {
        this.chainTxId = chainTxId;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public Integer getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(Integer blockNumber) {
        this.blockNumber = blockNumber;
    }

    public BigDecimal getTransferFee() {
        return transferFee;
    }

    public void setTransferFee(BigDecimal transferFee) {
        this.transferFee = transferFee;
    }
}