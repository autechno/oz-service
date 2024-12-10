package com.aucloud.aupay.user.orm.po;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 资金流水表
 * </p>
 *
 * @author yang.li@autech.one
 * @since 2024-12-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("acount_assets_record")
public class AccountAssetsRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 资产表id
     */
    private Integer assetsId;

    /**
     * 交易类型
     */
    private Integer tradeType;

    /**
     * 币种
     */
    private Integer currencyId;

    /**
     * 交易额
     */
    private BigDecimal amount;

    /**
     * 变动前余额
     */
    private BigDecimal beforeBalance;

    /**
     * 变动后余额
     */
    private BigDecimal afterBalance;

    /**
     * 交易费用
     */
    private BigDecimal fee;

    /**
     * 交易单号
     */
    private String tradeNo;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 交易创建时间
     */
    private Date createTime;

    /**
     * 完成时间
     */
    private Date finishTime;


}
