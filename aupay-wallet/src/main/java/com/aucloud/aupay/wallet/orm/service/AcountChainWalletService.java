package com.aucloud.aupay.wallet.orm.service;

import com.aucloud.aupay.wallet.orm.po.AccountChainWallet;
import com.aucloud.aupay.wallet.orm.mapper.AccountChainWalletMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 账号链上钱包表 服务实现类
 * </p>
 *
 * @author yang.li@autech.one
 * @since 2024-12-06
 */
@Service
public class AcountChainWalletService extends ServiceImpl<AccountChainWalletMapper, AccountChainWallet> implements IService<AccountChainWallet> {

}
