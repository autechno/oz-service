package com.aucloud.pojo.dto;

import lombok.Data;

@Data
public class ResetPasswordDTO {

    private String username;

    private String password;

    private String emailCode;

    private Long googleCode;

}
