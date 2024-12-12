package com.aucloud.commons.pojo.vo;

import lombok.Data;

import java.util.List;

@Data
public class UserInfoVo {

    private String userId;

    private String username;

    private String headPortrait;

    private String nickname;

    private String email;

    private String mobile;

    private String telegram;

    private Integer currencyUnit;

    private Boolean isSetAssetsPassword;

    private Boolean isBindGoogleAuth;

//    private List<UserAssetsVo> assetsVos;
}
