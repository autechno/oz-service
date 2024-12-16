package com.aucloud.aupay.security.entity;

public class Token {

    private Class tokenHeadInfo;

    private String cache;

    public Class getTokenHeadInfo() {
        return tokenHeadInfo;
    }

    public void setTokenHeadInfo(Class tokenHeadInfo) {
        this.tokenHeadInfo = tokenHeadInfo;
    }

    public String getCache() {
        return cache;
    }

    public void setCache(String cache) {
        this.cache = cache;
    }
}
