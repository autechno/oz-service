package com.aucloud.aupay.wallet.orm.service;

import com.aucloud.aupay.wallet.orm.constant.CollectEventType;
import com.aucloud.aupay.wallet.orm.mapper.ConfigWalletCollectMapper;
import com.aucloud.aupay.wallet.orm.po.ConfigWalletCollect;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 钱包归集配置 服务实现类
 * </p>
 *
 * @author yang.li@autech.one
 * @since 2024-12-09
 */
@Slf4j
@Service
public class ConfigWalletCollectService extends ServiceImpl<ConfigWalletCollectMapper, ConfigWalletCollect> implements IService<ConfigWalletCollect> {

    public ConfigWalletCollect getConfigWalletCollect(CollectEventType eventType, Integer currencyId, Integer currencyChain) {
        return lambdaQuery()
                .eq(ConfigWalletCollect::getEventType, eventType)
                .eq(ConfigWalletCollect::getCurrencyId, currencyId)
                .eq(ConfigWalletCollect::getCurrencyChain, currencyChain)
                .oneOpt().orElseThrow();
    }
}
