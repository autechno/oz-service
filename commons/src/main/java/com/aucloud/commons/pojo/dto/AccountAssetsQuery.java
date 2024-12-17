package com.aucloud.commons.pojo.dto;

import lombok.Data;

@Data
public class AccountAssetsQuery {

    private Long accountId;
    private Integer accountType;
    private Integer currencyId;
    private Integer currencyChain;
}
