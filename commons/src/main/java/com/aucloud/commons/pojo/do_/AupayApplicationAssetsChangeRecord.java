package com.aucloud.commons.pojo.do_;

import com.aucloud.commons.entity.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 【请填写功能名称】对象 aupay_application_assets_change_record
 *
 * @author ruoyi
 * @date 2024-07-02
 */
public class AupayApplicationAssetsChangeRecord extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private String orderId;

    /**
     * $column.columnComment
     */
    private String applicationOrderId;

    /**
     * $column.columnComment
     */
    private String applicationId;

    /**
     * $column.columnComment
     */
    private Integer currencyId;

    /**
     * $column.columnComment
     */
    private Integer currencyChain;

    /**
     * $column.columnComment
     */
    private BigDecimal beforeBalance;

    /**
     * $column.columnComment
     */
    private BigDecimal amount;

    /**
     * $column.columnComment
     */
    private BigDecimal fee;

    /**
     * $column.columnComment
     */
    private BigDecimal afterBalance;

    /**
     * $column.columnComment
     */
    private String fromAddress;

    /**
     * $column.columnComment
     */
    private String fromWalletId;

    /**
     * $column.columnComment
     */
    private String chainTxId;

    /**
     * $column.columnComment
     */
    private String toAddress;

    /**
     * $column.columnComment
     */
    private String toWalletId;

    /**
     * $column.columnComment
     */
    private Integer state;

    /**
     * $column.columnComment
     */
    private Integer tradeType;

    /**
     * $column.columnComment
     */
    private Date tranDate;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getApplicationOrderId() {
        return applicationOrderId;
    }

    public void setApplicationOrderId(String applicationOrderId) {
        this.applicationOrderId = applicationOrderId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
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

    public BigDecimal getBeforeBalance() {
        return beforeBalance;
    }

    public void setBeforeBalance(BigDecimal beforeBalance) {
        this.beforeBalance = beforeBalance;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public BigDecimal getAfterBalance() {
        return afterBalance;
    }

    public void setAfterBalance(BigDecimal afterBalance) {
        this.afterBalance = afterBalance;
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

    public String getChainTxId() {
        return chainTxId;
    }

    public void setChainTxId(String chainTxId) {
        this.chainTxId = chainTxId;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getToWalletId() {
        return toWalletId;
    }

    public void setToWalletId(String toWalletId) {
        this.toWalletId = toWalletId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getTradeType() {
        return tradeType;
    }

    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }

    public Date getTranDate() {
        return tranDate;
    }

    public void setTranDate(Date tranDate) {
        this.tranDate = tranDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("orderId", getOrderId())
                .append("applicationOrderId", getApplicationOrderId())
                .append("applicationId", getApplicationId())
                .append("currencyId", getCurrencyId())
                .append("currencyChain", getCurrencyChain())
                .append("beforeBalance", getBeforeBalance())
                .append("amount", getAmount())
                .append("fee", getFee())
                .append("afterBalance", getAfterBalance())
                .append("fromAddress", getFromAddress())
                .append("fromWalletId", getFromWalletId())
                .append("chainTxId", getChainTxId())
                .append("toAddress", getToAddress())
                .append("toWalletId", getToWalletId())
                .append("state", getState())
                .append("tradeType", getTradeType())
                .append("tranDate", getTranDate())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
