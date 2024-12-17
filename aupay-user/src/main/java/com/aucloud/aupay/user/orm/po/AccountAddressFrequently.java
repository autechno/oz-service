package com.aucloud.aupay.user.orm.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户地址簿
 * </p>
 *
 * @author yang.li@autech.one
 * @since 2024-12-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("account_address_frequently")
public class AccountAddressFrequently implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 账户id
     */
    private Long accountId;

    /**
     * 账户类型
     */
    private Integer accountType;

    /**
     * 地址类型
     */
    private Boolean white;

    /**
     * 币种
     */
    private Integer currencyId;

    /**
     * 链 协议
     */
    private Integer currencyChain;

    /**
     * 地址
     */
    private String address;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 删除状态
     */
    private Integer del;

    private Integer status;
    private Boolean lock;
    private Date unlockTime;

}
