package com.aucloud.aupay.user.orm.service;

import com.aucloud.aupay.user.orm.po.AupayUserLoginLog;
import com.aucloud.aupay.user.orm.mapper.AupayUserLoginLogMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yang.li@autech.one
 * @since 2024-12-11
 */
@Service
public class AupayUserLoginLogService extends ServiceImpl<AupayUserLoginLogMapper, AupayUserLoginLog> implements IService<AupayUserLoginLog> {

}
