package com.aucloud.aupay.user.orm.po;

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
 * 账户资产表
 * </p>
 *
 * @author yang.li@autech.one
 * @since 2024-12-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("account_assets")
public class AccountAssets implements Serializable {

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
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

    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 冻结余额
     */
    private BigDecimal freezeBalance;

    /**
     * 更新时间
     */
    private Date updateTime;


}
