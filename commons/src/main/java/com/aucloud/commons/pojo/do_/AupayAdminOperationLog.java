package com.aucloud.commons.pojo.do_;

import java.io.Serializable;
import java.util.Date;

public class AupayAdminOperationLog implements Serializable {
    private String id;

    private String adminId;

    private String ip;

    private String operationName;

    private String operationContent;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getOperationContent() {
        return operationContent;
    }

    public void setOperationContent(String operationContent) {
        this.operationContent = operationContent;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "AupayAdminOperationLog{" +
                "id='" + id + '\'' +
                ", adminId='" + adminId + '\'' +
                ", ip='" + ip + '\'' +
                ", operationName='" + operationName + '\'' +
                ", operationContent='" + operationContent + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}