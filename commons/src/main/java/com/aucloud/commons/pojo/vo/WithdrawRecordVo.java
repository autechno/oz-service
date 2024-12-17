package com.aucloud.commons.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class WithdrawRecordVo {

    private Long id;
    private Long accountId;
    private Integer accountType;
    /**
     * 资产表id
     */
    private Long assetsId;

    /**
     * 交易单号
     */
    private String tradeNo;

    /**
     * 币种
     */
    private Integer currencyId;

    /**
     * 链id
     */
    private Integer currencyChain;

    /**
     * 目的钱包地址
     */
    private String toAddress;

    /**
     * 提币额
     */
    private BigDecimal amount;

    /**
     * 费用
     */
    private BigDecimal fee;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 执行批次号
     */
    private String batchNo;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 完成时间
     */
    private Date finishTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 失败次数
     */
    private Integer failedTimes;
}
