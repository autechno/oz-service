package com.aucloud.aupay.wallet.orm.service;

import com.aucloud.aupay.wallet.orm.mapper.ConfigWithdrawMapper;
import com.aucloud.aupay.wallet.orm.po.ConfigWithdraw;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 提币配置 服务实现类
 * </p>
 *
 * @author yang.li@autech.one
 * @since 2024-12-09
 */
@Service
public class ConfigWithdrawService extends ServiceImpl<ConfigWithdrawMapper, ConfigWithdraw> implements IService<ConfigWithdraw> {

}
