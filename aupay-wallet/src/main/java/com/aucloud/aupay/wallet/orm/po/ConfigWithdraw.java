package com.aucloud.aupay.wallet.orm.po;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 提币配置
 * </p>
 *
 * @author yang.li@autech.one
 * @since 2024-12-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("config_withdraw")
public class ConfigWithdraw implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 币种
     */
    private Integer currencyId;

    /**
     * 链
     */
    private Integer currencyChain;

    /**
     * 提币上限
     */
    private BigDecimal withdrawMaxLimit;

    /**
     * 提币下限
     */
    private BigDecimal withdrawMinLimit;

    /**
     * 基础手续费
     */
    private BigDecimal baseFee;

    /**
     * 提币手续费
     */
    private BigDecimal feeRate;


}
