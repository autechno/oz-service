package com.aucloud.commons.pojo.do_;

import java.io.Serializable;
import java.util.Date;

public class AupayUserApplyApplication implements Serializable {
    private Integer id;

    private String userId;

    private String applicaitonId;

    private String applicaitonName;

    private Date createTime;

    private Boolean isDel;

    private String applicationUserId;

    private String applicationUsername;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getApplicaitonId() {
        return applicaitonId;
    }

    public void setApplicaitonId(String applicaitonId) {
        this.applicaitonId = applicaitonId;
    }

    public String getApplicaitonName() {
        return applicaitonName;
    }

    public void setApplicaitonName(String applicaitonName) {
        this.applicaitonName = applicaitonName;
    }

    public Boolean getDel() {
        return isDel;
    }

    public void setDel(Boolean del) {
        isDel = del;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getIsDel() {
        return isDel;
    }

    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
    }

    public String getApplicationUserId() {
        return applicationUserId;
    }

    public void setApplicationUserId(String applicationUserId) {
        this.applicationUserId = applicationUserId;
    }

    public String getApplicationUsername() {
        return applicationUsername;
    }

    public void setApplicationUsername(String applicationUsername) {
        this.applicationUsername = applicationUsername;
    }
}