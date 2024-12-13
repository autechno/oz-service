package com.aucloud.commons.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userId;

    private String username;

    private String headPortrait;

    private String relationUserId;

    private String relationUsername;

    private String relationEmail;

    private String password;

    private String email;

    private String emailCode;

    private String inviteCode;

    private int isBindPay;

    private String provider;

    private String providerId;
}
