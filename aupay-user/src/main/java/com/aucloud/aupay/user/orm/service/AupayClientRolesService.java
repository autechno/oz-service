package com.aucloud.aupay.user.orm.service;

import com.aucloud.aupay.user.orm.po.AupayClientRoles;
import com.aucloud.aupay.user.orm.mapper.AupayClientRolesMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * aupay客户端角色表 服务实现类
 * </p>
 *
 * @author yang.li@autech.one
 * @since 2024-12-17
 */
@Service
public class AupayClientRolesService extends ServiceImpl<AupayClientRolesMapper, AupayClientRoles> implements IService<AupayClientRoles> {

}
