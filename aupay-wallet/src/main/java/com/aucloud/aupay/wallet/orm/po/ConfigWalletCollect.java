package com.aucloud.aupay.wallet.orm.po;

import com.aucloud.aupay.wallet.orm.constant.CollectEventType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 钱包归集配置
 * </p>
 *
 * @author yang.li@autech.one
 * @since 2024-12-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("config_wallet_collect")
public class ConfigWalletCollect implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 任务事件类型
     */
    private CollectEventType eventType;

    /**
     * 币种
     */
    private Integer currencyId;

    /**
     * 币所在链
     */
    private Integer currencyChain;

    /**
     * 余额限额，超过此线了 归集;低于此线了 补充
     */
    private BigDecimal balanceLimit;

    /**
     * 若是补充，补充额
     */
    private BigDecimal supplyAmount;

    /**
     * 归集任务执行频率表达式
     */
    private String cron;

    /**
     * 任务是否暂停
     */
    private Integer pause;

    private LocalDateTime updateTime;


}
