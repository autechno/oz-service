package com.aucloud.aupay.wallet.orm.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 钱包配置表
 * </p>
 *
 * @author yang.li@autech.one
 * @since 2024-12-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("config_wallet_address")
public class ConfigWalletAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 钱包类型
     */
    private Integer walletType;

    /**
     * 链
     */
    private Integer currencyChain;

    /**
     * 钱包地址
     */
    private String walletAddress;


}
