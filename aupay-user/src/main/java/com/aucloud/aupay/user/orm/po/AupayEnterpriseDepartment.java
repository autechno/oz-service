package com.aucloud.aupay.user.orm.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * Aupay企业部门表
 * </p>
 *
 * @author yang.li@autech.one
 * @since 2024-12-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("aupay_enterprise_department")
public class AupayEnterpriseDepartment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 企业id
     */
    private Long enterpriseId;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 上级部门id
     */
    private Long parentId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 是否删除
     */
    private Integer del;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


}
