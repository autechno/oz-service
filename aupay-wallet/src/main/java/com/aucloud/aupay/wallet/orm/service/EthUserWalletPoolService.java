package com.aucloud.aupay.wallet.orm.service;

import com.aucloud.aupay.wallet.orm.po.EthUserWalletPool;
import com.aucloud.aupay.wallet.orm.mapper.EthUserWalletPoolMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * Eth链用户钱包池子 服务实现类
 * </p>
 *
 * @author yang.li@autech.one
 * @since 2024-12-06
 */
@Service
public class EthUserWalletPoolService extends ServiceImpl<EthUserWalletPoolMapper, EthUserWalletPool> implements IService<EthUserWalletPool> {

}
