package com.aucloud.aupay.wallet.orm.service;

import com.aucloud.aupay.wallet.orm.mapper.ConfigWalletAddressMapper;
import com.aucloud.aupay.wallet.orm.po.ConfigWalletAddress;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 钱包配置表 服务实现类
 * </p>
 *
 * @author yang.li@autech.one
 * @since 2024-12-09
 */
@Service
public class ConfigWalletAddressService extends ServiceImpl<ConfigWalletAddressMapper, ConfigWalletAddress> implements IService<ConfigWalletAddress> {

    public String getWalletAddress(Integer walletType, Integer currencyChain) {
        ConfigWalletAddress config = lambdaQuery()
                .eq(ConfigWalletAddress::getWalletType, walletType)
                .eq(ConfigWalletAddress::getCurrencyChain, currencyChain)
                .oneOpt().orElseThrow();
        return config.getWalletAddress();
    }
}
