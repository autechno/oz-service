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
 * 用户类型表
 * </p>
 *
 * @author yang.li@autech.one
 * @since 2024-12-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("aupay_user_type")
public class AupayUserType implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 类型编码
     */
    @TableId(value = "type_code", type = IdType.AUTO)
    private String typeCode;

    /**
     * 类型名称
     */
    private String typeName;

    /**
     * 创建者
     */
    private Long creatorId;

    /**
     * 最后编辑者
     */
    private Long latestEditorId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    private Integer del;


}
