package com.aucloud.aupay.security.token;

import com.alibaba.fastjson2.JSON;
import lombok.Data;

@Data
public class TokenInfo {

    private String id;

    private Integer terminal;

    public static String makeTokenInfo(String id, Integer terminal) {
        TokenInfo adminTokenInfo = new TokenInfo();
        adminTokenInfo.setId(id);
        adminTokenInfo.setTerminal(terminal);
        return JSON.toJSONString(adminTokenInfo);
    }

}
