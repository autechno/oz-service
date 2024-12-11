package com.aucloud.aupay.wallet.orm.po;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * Eth链用户钱包池子
 * </p>
 *
 * @author yang.li@autech.one
 * @since 2024-12-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eth_user_wallet_pool")
public class EthUserWalletPool implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 钱包地址
     */
    private String walletAddress;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 被使用用户id
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private Integer userId;

    /**
     * 被回收时间
     */
    private Date recycleTime;

    /**
     * 启用时间
     */
    private Date inuseTime;


}
