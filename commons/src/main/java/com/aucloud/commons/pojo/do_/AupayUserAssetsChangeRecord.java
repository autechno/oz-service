package com.aucloud.commons.pojo.do_;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class AupayUserAssetsChangeRecord implements Serializable {
    private String id;

    private String applicationOrderId;

    private Date createTime;

    private String userId;

    private String username;

    private Integer currencyId;

    private Integer currencyChain;

    private Integer tradeType;

    private BigDecimal beforeBalance;

    private BigDecimal amount;

    private BigDecimal fee;

    private BigDecimal afterBalance;

    private String fromAddress;

    private String fromWalletId;

    private String chainTxId;

    private String toAddress;

    private String toWalletId;

    private Integer state;

    private static final long serialVersionUID = 1L;

}