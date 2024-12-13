package com.aucloud.aupay.user.orm.service;

import com.aucloud.aupay.user.orm.po.AupayEnterpriseEmployee;
import com.aucloud.aupay.user.orm.mapper.AupayEnterpriseEmployeeMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * Aupay企业员工关系表 服务实现类
 * </p>
 *
 * @author yang.li@autech.one
 * @since 2024-12-13
 */
@Service
public class AupayEnterpriseEmployeeService extends ServiceImpl<AupayEnterpriseEmployeeMapper, AupayEnterpriseEmployee> implements IService<AupayEnterpriseEmployee> {

}
