package com.aucloud.aupay.user.orm.service;

import com.aucloud.aupay.user.orm.po.AupayClientEmployeeRoles;
import com.aucloud.aupay.user.orm.mapper.AupayClientEmployeeRolesMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * aupay客户端员工权限表 服务实现类
 * </p>
 *
 * @author yang.li@autech.one
 * @since 2024-12-17
 */
@Service
public class AupayClientEmployeeRolesService extends ServiceImpl<AupayClientEmployeeRolesMapper, AupayClientEmployeeRoles> implements IService<AupayClientEmployeeRoles> {

}
