package com.aucloud.commons.pojo.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class EthTransactionCallback {

    private String innerHash;
    private String hash;
    private boolean status;
    private String error;

    BigDecimal gasUsed;
    BigDecimal blockNumber;
}
