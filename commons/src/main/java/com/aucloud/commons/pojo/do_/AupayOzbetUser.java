package com.aucloud.commons.pojo.do_;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AupayOzbetUser implements Serializable {
    private String userId;

    private Date createTime;

    private BigDecimal balanceOfOzc;

    private String ozbetUsername;

    private String ozbetUserId;

    private static final long serialVersionUID = 1L;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getBalanceOfOzc() {
        return balanceOfOzc;
    }

    public void setBalanceOfOzc(BigDecimal balanceOfOzc) {
        this.balanceOfOzc = balanceOfOzc;
    }

    public String getOzbetUsername() {
        return ozbetUsername;
    }

    public void setOzbetUsername(String ozbetUsername) {
        this.ozbetUsername = ozbetUsername;
    }

    public String getOzbetUserId() {
        return ozbetUserId;
    }

    public void setOzbetUserId(String ozbetUserId) {
        this.ozbetUserId = ozbetUserId;
    }
}