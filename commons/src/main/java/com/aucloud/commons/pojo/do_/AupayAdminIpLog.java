package com.aucloud.commons.pojo.do_;

import java.io.Serializable;
import java.util.Date;

public class AupayAdminIpLog implements Serializable {
    private String id;

    private String ip;

    private String note;

    private Date ipCreateTime;

    private Date createTime;

    private String action;

    private String adminId;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getIpCreateTime() {
        return ipCreateTime;
    }

    public void setIpCreateTime(Date ipCreateTime) {
        this.ipCreateTime = ipCreateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }
}