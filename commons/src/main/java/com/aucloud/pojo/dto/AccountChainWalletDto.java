package com.aucloud.pojo.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AccountChainWalletDto {

    private Integer id;

    /**
     * 账户id
     */
    private Integer acountId;

    /**
     * 账户类型
     */
    private Integer acountType;

    /**
     * 链
     */
    private Integer currencyChain;

    /**
     * 钱包地址
     */
    private String walletAddress;

    /**
     * 用户钱包池子id
     */
    private Integer walletPoolId;

    /**
     * 是否删除
     */
    private Integer del;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
