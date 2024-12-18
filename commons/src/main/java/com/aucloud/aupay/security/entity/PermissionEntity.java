package com.aucloud.aupay.security.entity;

public class PermissionEntity {

    private Integer id;

    private String permission;

    private String parentPermission;

    private boolean childrenObtain;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getParentPermission() {
        return parentPermission;
    }

    public void setParentPermission(String parentPermission) {
        this.parentPermission = parentPermission;
    }

    public boolean isChildrenObtain() {
        return childrenObtain;
    }

    public void setChildrenObtain(boolean childrenObtain) {
        this.childrenObtain = childrenObtain;
    }

    @Override
    public String toString() {
        return "PermissionEntity{" +
                "id=" + id +
                ", permission='" + permission + '\'' +
                ", parentPermission='" + parentPermission + '\'' +
                ", childrenObtain=" + childrenObtain +
                '}';
    }
}
