package com.aucloud.aupay.security.entity;

import com.alibaba.fastjson2.annotation.JSONField;

import java.util.Date;


public class TokenHeadInfo {

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date expirationTime;

    private String role;

    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}