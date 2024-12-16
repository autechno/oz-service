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
 * Aupay企业员工关系表
 * </p>
 *
 * @author yang.li@autech.one
 * @since 2024-12-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("aupay_enterprise_employee")
public class AupayEnterpriseEmployee implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 企业id
     */
    private Long enterpriseId;

    /**
     * 员工id
     */
    private Long employeeId;

    /**
     * 职位
     */
    private String job;

    /**
     * 部门id
     */
    private Long departmentId;

    /**
     * 入职时间
     */
    private LocalDateTime joinTime;

    /**
     * 离职时间
     */
    private LocalDateTime leaveTime;

    /**
     * 记录创建日期
     */
    private LocalDateTime createTime;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 是否删除
     */
    private Integer del;

    private String remark;


}
