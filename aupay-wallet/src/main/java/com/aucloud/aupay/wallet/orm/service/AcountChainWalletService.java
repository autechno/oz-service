package com.aucloud.aupay.wallet.orm.service;

import com.aucloud.aupay.wallet.orm.po.AccountChainWallet;
import com.aucloud.aupay.wallet.orm.mapper.AccountChainWalletMapper;
import com.aucloud.aupay.wallet.orm.po.EthUserWalletPool;
import com.aucloud.constant.CurrencyEnum;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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

    @Autowired
    private EthUserWalletPoolService ethUserWalletPoolService;

    public AccountChainWallet createAccountChainWallet(Integer accountId, Integer accountType, Integer currencyChain) {
        AccountChainWallet accountChainWallet = null;
        CurrencyEnum.CurrencyChainEnum chainEnum = CurrencyEnum.CurrencyChainEnum.findById(currencyChain);
        switch (chainEnum) {
            case ETH -> accountChainWallet = createEthWallet(accountId, accountType);
            case BTC -> {}
            case TRC -> {}
        }
        return accountChainWallet;
    }

    private AccountChainWallet createEthWallet(Integer accountId, Integer accountType) {
        AccountChainWallet accountChainWallet = lambdaQuery()
                .eq(AccountChainWallet::getAcountId, accountId)
                .eq(AccountChainWallet::getAcountType, accountType)
                .eq(AccountChainWallet::getCurrencyChain, CurrencyEnum.CurrencyChainEnum.ETH.id)
                .oneOpt().orElseGet(AccountChainWallet::new);
        if (accountChainWallet.getId() != null) {
            Integer walletPoolId = accountChainWallet.getWalletPoolId();
            EthUserWalletPool pool = ethUserWalletPoolService.getById(walletPoolId);
            ethUserWalletPoolService.recycleUserWallet(pool);
        }
        EthUserWalletPool walletAddress = ethUserWalletPoolService.getUnusedWalletAddress();
        if (Objects.nonNull(walletAddress)) {
            accountChainWallet.setWalletAddress(walletAddress.getWalletAddress());
            accountChainWallet.setAcountId(accountId);
            accountChainWallet.setAcountType(accountType);
            accountChainWallet.setCurrencyChain(CurrencyEnum.CurrencyChainEnum.ETH.id);
            accountChainWallet.setWalletPoolId(walletAddress.getId());
            saveOrUpdate(accountChainWallet);

            walletAddress.setStatus(1);
            walletAddress.setInuseTime(new Date());
            walletAddress.setUserId(accountChainWallet.getId());
            ethUserWalletPoolService.updateById(walletAddress);
            return accountChainWallet;
        }
        return null;
    }

    public List<AccountChainWallet> generateAccountWallet(Integer accountId, Integer accountType) {
        CurrencyEnum.CurrencyChainEnum[] values = CurrencyEnum.CurrencyChainEnum.values();
        List<AccountChainWallet> list = new ArrayList<>();
        for (CurrencyEnum.CurrencyChainEnum chainEnum : values) {
            switch (chainEnum) {
                case ETH -> list.add(createEthWallet(accountId, accountType));
                case BTC -> {}
                case TRC -> {}
            }
        }
        return list;
    }
}
