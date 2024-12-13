package com.aucloud.aupay.wallet.orm.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 提币任务表
 * </p>
 *
 * @author yang.li@autech.one
 * @since 2024-12-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("withdraw_task")
public class WithdrawTask implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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
