package com.aucloud.commons.pojo.do_;

import java.io.Serializable;
import java.util.Date;

public class AupayAnnouncement implements Serializable {
    private String id;

    private Date createTime;

    private Boolean isDel;

    private String title;

    private String content;

    private String creator;

    private Boolean isShow;

    private Boolean isRoll;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Boolean getIsShow() {
        return isShow;
    }

    public void setIsShow(Boolean isShow) {
        this.isShow = isShow;
    }

    public Boolean getIsRoll() {
        return isRoll;
    }

    public void setIsRoll(Boolean isRoll) {
        this.isRoll = isRoll;
    }
}