package com.aucloud.aupay.security.entity;

public class Settings {

    private Token token;

    private boolean interceptDfault; //默认是否放行

    private String permissionVerifyMode;

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public boolean isInterceptDfault() {
        return interceptDfault;
    }

    public void setInterceptDfault(boolean interceptDfault) {
        this.interceptDfault = interceptDfault;
    }

    public String getPermissionVerifyMode() {
        return permissionVerifyMode;
    }

    public void setPermissionVerifyMode(String permissionVerifyMode) {
        this.permissionVerifyMode = permissionVerifyMode;
    }
}
