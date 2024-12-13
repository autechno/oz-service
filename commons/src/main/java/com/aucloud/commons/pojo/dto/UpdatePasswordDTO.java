package com.aucloud.commons.pojo.dto;

import lombok.Data;

@Data
public class UpdatePasswordDTO {

    private String oldPassword;

    private String newPassword;

}
