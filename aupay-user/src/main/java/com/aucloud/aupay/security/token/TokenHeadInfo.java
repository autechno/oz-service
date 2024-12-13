package com.aucloud.aupay.security.token;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.Date;

@Data
public class TokenHeadInfo {

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date expirationTime;

    private String role;

    public static String getTokenHead(Date date) {
        TokenHeadInfo tokenHeadInfo = new TokenHeadInfo();
        tokenHeadInfo.setExpirationTime(date);
        return JSON.toJSONString(tokenHeadInfo);
    }
}