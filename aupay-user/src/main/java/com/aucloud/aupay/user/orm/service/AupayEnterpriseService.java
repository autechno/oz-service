package com.aucloud.aupay.user.orm.service;

import com.aucloud.aupay.user.orm.po.AupayEnterprise;
import com.aucloud.aupay.user.orm.mapper.AupayEnterpriseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户企业表 服务实现类
 * </p>
 *
 * @author yang.li@autech.one
 * @since 2024-12-13
 */
@Service
public class AupayEnterpriseService extends ServiceImpl<AupayEnterpriseMapper, AupayEnterprise> implements IService<AupayEnterprise> {

}
