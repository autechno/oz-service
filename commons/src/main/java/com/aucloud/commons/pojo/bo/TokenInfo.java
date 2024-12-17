package com.aucloud.commons.pojo.bo;

import com.alibaba.fastjson2.JSON;
import lombok.Data;

@Data
public class TokenInfo {
    private Integer terminal;

    private Long userId;//登录用户id
    private Integer accountType;//登录的账号类型，个人 & 企业
    private Long accountId;//登录的账户的id 个人类型就是userId，企业账户就是企业id
    private Long employeeId;//企业类型时，在该企业中的员工id

    public static String makeTokenInfo(Long id, Integer terminal) {
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setUserId(id);
        tokenInfo.setTerminal(terminal);
        return JSON.toJSONString(tokenInfo);
    }

}
