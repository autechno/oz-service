package com.aucloud.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AccountAssetsVo {

    private Integer id;

    /**
     * 账户id
     */
    private Integer accountId;

    /**
     * 账户类型
     */
    private Integer accountType;

    /**
     * 币种
     */
    private Integer currencyId;
    private Integer currencyChain;
    private String walletAddress;

    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 冻结余额
     */
    private BigDecimal freezeBalance;

    private BigDecimal totalBalance;

    /**
     * 更新时间
     */
    private Date updateTime;
}
