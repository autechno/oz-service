package com.aucloud.aupay.user.orm.service;

import com.aucloud.aupay.user.orm.po.AupayClientPermissions;
import com.aucloud.aupay.user.orm.mapper.AupayClientPermissionsMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * aupay客户端权限表 服务实现类
 * </p>
 *
 * @author yang.li@autech.one
 * @since 2024-12-17
 */
@Service
public class AupayClientPermissionsService extends ServiceImpl<AupayClientPermissionsMapper, AupayClientPermissions> implements IService<AupayClientPermissions> {

}
