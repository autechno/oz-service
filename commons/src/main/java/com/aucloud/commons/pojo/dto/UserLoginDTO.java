package com.aucloud.commons.pojo.dto;

import lombok.Data;

@Data
public class UserLoginDTO {

    private String username;

    private String password;

    private Integer emailCode;

    private String thirdPartMerchant;

}
