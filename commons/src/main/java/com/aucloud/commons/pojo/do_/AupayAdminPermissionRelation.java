package com.aucloud.commons.pojo.do_;

import java.io.Serializable;

public class AupayAdminPermissionRelation implements Serializable {
    private String adminId;

    private Integer permissionId;

    private static final long serialVersionUID = 1L;

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }
}