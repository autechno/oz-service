package com.aucloud.aupay.wallet.orm.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 内部钱包资金归集周转任务记录表
 * </p>
 *
 * @author yang.li@autech.one
 * @since 2024-12-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wallet_collect_task_record")
public class WalletCollectTaskRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 任务执行交易号
     */
    private String tradeNo;

    /**
     * 归集配置表id
     */
    private Integer configId;

    /**
     * 任务执行状态
     */
    private Integer status;

    /**
     * 任务执行创建时间
     */
    private Date createTime;

    /**
     * 任务结束时间
     */
    private Date finishTime;

    /**
     * 任务执行失败时导入另一个任务id
     */
    private Integer dependTaskId;

    /**
     * 备注
     */
    private String remark;


}
