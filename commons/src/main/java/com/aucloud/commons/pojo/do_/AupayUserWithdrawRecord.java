package com.aucloud.commons.pojo.do_;

import com.aucloud.commons.constant.RecordType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class AupayUserWithdrawRecord implements Serializable {
    private String id;

    private Date createTime;

    private String userId;

    private Integer currencyChain;

    private Integer currencyId;

    private BigDecimal amount;

    private String fromAddress;

    private String fromWalletId;

    private String toAddress;

    private String chainTxId;

    private Integer blockNum;

    private Date finishTime;

    private Integer state;

    private String assetsChangeRecordId;

    private Integer recordType = RecordType.USER_WITHDRAW;

    private static final long serialVersionUID = 1L;

}