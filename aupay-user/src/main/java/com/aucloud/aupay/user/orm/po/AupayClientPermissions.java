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
 * aupay客户端权限表
 * </p>
 *
 * @author yang.li@autech.one
 * @since 2024-12-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("aupay_client_permissions")
public class AupayClientPermissions implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String permissionCode;

    /**
     * 权限url?
     */
    private String permissionUrl;

    /**
     * 权限名称
     */
    private String permissionName;

    /**
     * 父权限id
     */
    private Long parentPermissionId;


}
