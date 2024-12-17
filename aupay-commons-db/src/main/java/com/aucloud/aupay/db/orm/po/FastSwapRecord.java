package com.aucloud.aupay.db.orm.po;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 闪兑记录表
 * </p>
 *
 * @author yang.li@autech.one
 * @since 2024-12-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("fast_swap_record")
public class FastSwapRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 交易流水号
     */
    private String tradeNo;

    /**
     * 账户id
     */
    private Long accountId;

    /**
     * 账户类型
     */
    private Integer accountType;

    /**
     * 操作者id
     */
    private Long operatorId;

    /**
     * 兑出-资金表id
     */
    private Long cashOutAssetsId;

    /**
     * 兑出币种
     */
    private Integer cashOutCurrencyId;

    /**
     * 兑出链
     */
    private Integer cashOutChain;

    /**
     * 兑出额
     */
    private BigDecimal cashOutAmount;

    /**
     * 兑入-资金表id
     */
    private Long cashInAssetsId;

    /**
     * 兑入币种
     */
    private Integer cashInCurrencyId;

    /**
     * 兑入链
     */
    private Integer cashInChain;

    /**
     * 兑入额
     */
    private BigDecimal cashInAmount;

    /**
     * 手续费
     */
    private BigDecimal fee;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 完成时间
     */
    private LocalDateTime finishTime;

    private String remark;


}
