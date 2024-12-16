package com.aucloud.commons.pojo.do_;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class AupayReserveTransferWalletRecord implements Serializable {
    private String id;

    private Integer currencyId;

    private Integer currencyChain;

    private String fromWalletId;

    private String toWalletId;

    private BigDecimal amount;

    private String transferRecordId;

    private Integer state;

    private static final long serialVersionUID = 1L;

}