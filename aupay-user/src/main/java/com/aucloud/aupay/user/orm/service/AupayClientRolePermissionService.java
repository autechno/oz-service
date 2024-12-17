package com.aucloud.aupay.user.orm.service;

import com.aucloud.aupay.user.orm.po.AupayClientRolePermission;
import com.aucloud.aupay.user.orm.mapper.AupayClientRolePermissionMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色权限表 服务实现类
 * </p>
 *
 * @author yang.li@autech.one
 * @since 2024-12-17
 */
@Service
public class AupayClientRolePermissionService extends ServiceImpl<AupayClientRolePermissionMapper, AupayClientRolePermission> implements IService<AupayClientRolePermission> {

}
