package com.aucloud.aupay.security.entity;

import java.util.List;

public class RoleEntity {

    private String role;

    private List<Integer> permissions;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Integer> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Integer> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "RoleEntity{" +
                "role='" + role + '\'' +
                ", permissions=" + permissions +
                '}';
    }
}
