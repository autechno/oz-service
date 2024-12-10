package com.aucloud.aupay.wallet.orm.po;

import com.aucloud.aupay.wallet.orm.constant.TradeType;
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
 * 链上交易流水表
 * </p>
 *
 * @author yang.li@autech.one
 * @since 2024-12-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wallet_transfer_record")
public class WalletTransferRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 交易流水
     */
    private String tradeNo;

    /**
     * 交易类型
     */
    private TradeType tradeType;

    /**
     * 转出地址
     */
    private String fromAddress;

    /**
     * 转入地址
     */
    private String toAddress;

    /**
     * 交易hash
     */
    private String txHash;

    /**
     * 币种
     */
    private Integer currencyId;

    /**
     * 链
     */
    private Integer currencyChain;

    /**
     * 交易额
     */
    private BigDecimal amount;

    /**
     * 交易费用
     */
    private BigDecimal fee;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建交易时间
     */
    private Date createTime;

    /**
     * 完成时间
     */
    private Date finishTime;


}
