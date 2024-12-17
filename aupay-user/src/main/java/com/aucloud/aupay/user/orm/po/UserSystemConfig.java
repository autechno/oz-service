package com.aucloud.aupay.user.orm.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户系统设置表
 * </p>
 *
 * @author yang.li@autech.one
 * @since 2024-12-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_system_config")
public class UserSystemConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 人的id(个人或员工)
     */
    private Long humanId;

    /**
     * 人的类型(个人或员工)
     */
    private Integer humanType;

    /**
     * 货币单位
     */
    private String currencyUnit;

    /**
     * 显示隐藏开关
     */
    private Integer showHide;

    /**
     * 系统语言
     */
    private String systemLanguage;


}
