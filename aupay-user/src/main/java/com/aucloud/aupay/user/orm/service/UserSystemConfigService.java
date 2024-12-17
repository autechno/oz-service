package com.aucloud.aupay.user.orm.service;

import com.aucloud.aupay.user.orm.po.UserSystemConfig;
import com.aucloud.aupay.user.orm.mapper.UserSystemConfigMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户系统设置表 服务实现类
 * </p>
 *
 * @author yang.li@autech.one
 * @since 2024-12-17
 */
@Service
public class UserSystemConfigService extends ServiceImpl<UserSystemConfigMapper, UserSystemConfig> implements IService<UserSystemConfig> {

}
