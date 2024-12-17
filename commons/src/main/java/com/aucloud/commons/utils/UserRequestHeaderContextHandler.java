package com.aucloud.commons.utils;

import com.aucloud.commons.pojo.bo.TokenInfo;

public class UserRequestHeaderContextHandler {

    private static final ThreadLocal<TokenInfo> threadLocal = new ThreadLocal<>();

    public static TokenInfo getTokenInfo() {
        return threadLocal.get();
    }

    public static void setTokenInfo(TokenInfo tokenInfo) {
        threadLocal.set(tokenInfo);
    }

    public static void removeTokenInfo() {
        threadLocal.remove();
    }
}
