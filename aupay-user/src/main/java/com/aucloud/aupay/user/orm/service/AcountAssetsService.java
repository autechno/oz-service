package com.aucloud.aupay.user.orm.service;

import com.aucloud.aupay.user.orm.po.AcountAssets;
import com.aucloud.aupay.user.orm.mapper.AcountAssetsMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 账户资产表 服务实现类
 * </p>
 *
 * @author yang.li@autech.one
 * @since 2024-12-06
 */
@Service
public class AcountAssetsService extends ServiceImpl<AcountAssetsMapper, AcountAssets> implements IService<AcountAssets> {

}
