package com.aucloud.aupay.wallet.orm.po;

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
 * 账号链上钱包表
 * </p>
 *
 * @author yang.li@autech.one
 * @since 2024-12-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("acount_chain_wallet")
public class AcountChainWallet implements Serializable {

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
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
