package com.aucloud.commons.pojo.do_;

import com.aucloud.commons.entity.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 【请填写功能名称】对象 aupay_application_assets
 * 
 * @author ruoyi
 * @date 2024-07-03
 */
public class AupayApplicationAssets extends BaseEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String id;

    /** $column.columnComment */
    private String applicationId;

    /** $column.columnComment */
    private Integer currencyId;

    /** $column.columnComment */
    private Integer currencyChain;

    /** $column.columnComment */
    private BigDecimal balance;

    /** $column.columnComment */
    private BigDecimal freezeBalance;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setApplicationId(String applicationId) 
    {
        this.applicationId = applicationId;
    }

    public String getApplicationId() 
    {
        return applicationId;
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

    public void setBalance(BigDecimal balance)
    {
        this.balance = balance;
    }

    public BigDecimal getBalance() 
    {
        return balance;
    }
    public void setFreezeBalance(BigDecimal freezeBalance) 
    {
        this.freezeBalance = freezeBalance;
    }

    public BigDecimal getFreezeBalance() 
    {
        return freezeBalance;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
