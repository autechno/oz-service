package com.aucloud.commons.pojo.do_;

import com.aucloud.commons.constant.RecordType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class AupayUserTradeRecord implements Serializable {

    private String id;

    private String applicationOrderId;

    private String applicationId;

    private Date createTime;

    private String userId;

    private String username;

    private Integer tradeType;

    private Integer currencyId;

    private Integer currencyChain;

    private BigDecimal amount;

    private String instruction;

    private String fromWalletId;

    private String toWalletId;

    private String chainTxId;

    private Date finishTime;

    private Integer state;

    private String assetsChangeRecordId;

    private Integer recordType = RecordType.USER_TRADE;

    private static final long serialVersionUID = 1L;

}