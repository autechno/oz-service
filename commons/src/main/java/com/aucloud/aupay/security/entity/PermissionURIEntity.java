package com.aucloud.aupay.security.entity;

public class PermissionURIEntity {

    private String requestURI;

    private String permission;

    public String getRequestURI() {
        return requestURI;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        return "PermissionURIEntity{" +
                "requestURI='" + requestURI + '\'' +
                ", permission='" + permission + '\'' +
                '}';
    }
}
