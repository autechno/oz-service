package com.aucloud.aupay.user.orm.service;

import com.aucloud.aupay.user.orm.po.AupayUserType;
import com.aucloud.aupay.user.orm.mapper.AupayUserTypeMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户类型表 服务实现类
 * </p>
 *
 * @author yang.li@autech.one
 * @since 2024-12-13
 */
@Service
public class AupayUserTypeService extends ServiceImpl<AupayUserTypeMapper, AupayUserType> implements IService<AupayUserType> {

}
