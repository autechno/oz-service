package com.aucloud.commons.pojo.dto;

import lombok.Data;

@Data
public class WithdrawRecordQuery {

    private Long accountId;
    private Integer accountType;

    private Long assetsId;
    private String tradeNo;
}
