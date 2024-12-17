package com.aucloud.commons.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 链上交易流水表
 * </p>
 *
 * @author yang.li@autech.one
 * @since 2024-12-06
 */
@Data
public class WalletTransferRecordVo implements Serializable {


    /**
     * 主键id
     */
    private Long id;

    /**
     * 交易流水
     */
    private String tradeNo;

    /**
     * 交易类型
     */
    private Integer tradeType;

    /**
     * 转出地址
     */
    private String fromAddress;

    /**
     * 转入地址
     */
    private String toAddress;

    /**
     * 交易hash
     */
    private String txHash;

    /**
     * 币种
     */
    private Integer currencyId;

    /**
     * 链
     */
    private Integer currencyChain;

    /**
     * 交易额
     */
    private BigDecimal amount;

    /**
     * 交易费用
     */
    private BigDecimal fee;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建交易时间
     */
    private Date createTime;

    /**
     * 完成时间
     */
    private Date finishTime;


}
