package com.aucloud.aupay.user.service;

import com.aucloud.aupay.user.orm.po.AccountAssets;
import com.aucloud.aupay.user.orm.po.AccountAssetsRecord;
import com.aucloud.aupay.user.orm.service.AccountAssetsRecordService;
import com.aucloud.aupay.user.orm.service.AccountAssetsService;
import com.aucloud.commons.constant.CurrencyEnum;
import com.aucloud.commons.constant.ResultCodeEnum;
import com.aucloud.commons.constant.TradeType;
import com.aucloud.commons.exception.ServiceRuntimeException;
import com.aucloud.commons.pojo.PageQuery;
import com.aucloud.commons.pojo.dto.AccountAssetsQuery;
import com.aucloud.commons.pojo.dto.AccountAssetsRecordQuery;
import com.aucloud.commons.pojo.dto.AcountRechargeDTO;
import com.aucloud.commons.pojo.dto.WithdrawDTO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class AssetsService {

    @Autowired
    private AccountAssetsService accountAssetsService;
    @Autowired
    private AccountAssetsRecordService accountAssetsRecordService;

    public List<AccountAssets> getAccountAssets(AccountAssetsQuery query) {
        Long accountId = query.getAccountId();
        Integer accountType = query.getAccountType();
        Integer currencyId = query.getCurrencyId();
        Integer currencyChain = query.getCurrencyChain();
        LambdaQueryWrapper<AccountAssets> queryWrapper = new LambdaQueryWrapper<>();
        if (accountId != null) {
            queryWrapper.eq(AccountAssets::getAccountId, accountId);
        }
        if (accountType != null) {
            queryWrapper.eq(AccountAssets::getAccountType, accountType);
        }
        if (currencyId != null) {
            queryWrapper.eq(AccountAssets::getCurrencyId, currencyId);
        }
        if (currencyChain != null) {
            queryWrapper.eq(AccountAssets::getCurrencyChain, currencyChain);
        }
        return accountAssetsService.list(queryWrapper);
    }

    public Page<AccountAssetsRecord> getAssetsRecords(PageQuery<AccountAssetsRecordQuery> pageQuery) {
        AccountAssetsRecordQuery conditions = pageQuery.getConditions();
        Page<AccountAssetsRecord> page = new Page<>(pageQuery.getPageNo(), pageQuery.getPageSize());
        return accountAssetsRecordService.getBaseMapper().getAssetsRecords(page, conditions);
    }

    public String preDeduct(WithdrawDTO withdrawDTO) {
        AccountAssets acountAssets = accountAssetsService.lambdaQuery()
                .eq(AccountAssets::getAccountId, withdrawDTO.getAccountId())
                .eq(AccountAssets::getAccountType, withdrawDTO.getAccountType())
                .eq(AccountAssets::getCurrencyId, withdrawDTO.getCurrencyId())
                .oneOpt().orElseGet(() -> createNewAssets(withdrawDTO.getAccountId(), withdrawDTO.getAccountType(), withdrawDTO.getCurrencyChain(),withdrawDTO.getCurrencyId()));
        BigDecimal balance = acountAssets.getBalance() == null ? BigDecimal.ZERO : acountAssets.getBalance();
        BigDecimal amount = withdrawDTO.getAmount();
        BigDecimal fee = withdrawDTO.getFee();
        BigDecimal deduct = amount.add(fee);
        if (balance.compareTo(deduct) < 0) {
            throw new ServiceRuntimeException(ResultCodeEnum.INSUFFICIENT_BALANCE);
        }
        acountAssets.setBalance(balance.subtract(deduct));
        BigDecimal freezeBalance = acountAssets.getFreezeBalance() == null ? BigDecimal.ZERO : acountAssets.getFreezeBalance();
        acountAssets.setFreezeBalance(freezeBalance.add(deduct));
        acountAssets.setUpdateTime(new Date());
        accountAssetsService.saveOrUpdate(acountAssets);

        String tradeNo = UUID.randomUUID().toString();
        AccountAssetsRecord accountAssetsRecord = new AccountAssetsRecord();
        accountAssetsRecord.setAssetsId(acountAssets.getId());
        accountAssetsRecord.setTradeNo(tradeNo);
        accountAssetsRecord.setAmount(amount);
        accountAssetsRecord.setFee(fee);
        accountAssetsRecord.setAfterBalance(balance.subtract(deduct));
        accountAssetsRecord.setStatus(0);
        accountAssetsRecord.setCurrencyId(withdrawDTO.getCurrencyId());
        accountAssetsRecord.setBeforeBalance(balance);
        accountAssetsRecord.setTradeType(TradeType.WITHDRAW.getCode());
        accountAssetsRecordService.save(accountAssetsRecord);
        return tradeNo;
    }

    public void recharge(AcountRechargeDTO dto) {
        AccountAssets acountAssets = accountAssetsService.lambdaQuery()
                .eq(AccountAssets::getAccountId, dto.getAccountId())
                .eq(AccountAssets::getAccountType, dto.getAccountType())
                .eq(AccountAssets::getCurrencyId, dto.getCurrencyEnum().id)
                .oneOpt().orElseGet(() -> createNewAssets(dto.getAccountId(), dto.getAccountType(), dto.getChainEnum().id ,dto.getCurrencyEnum().id));
        BigDecimal balance = acountAssets.getBalance() == null ? BigDecimal.ZERO : acountAssets.getBalance();
        acountAssets.setBalance(balance.add(dto.getAmount()));
        accountAssetsService.saveOrUpdate(acountAssets);

        AccountAssetsRecord acountAssetsRecord = new AccountAssetsRecord();
        acountAssetsRecord.setAssetsId(acountAssets.getId());
        acountAssetsRecord.setTradeNo(dto.getTradeNo());
        acountAssetsRecord.setAmount(dto.getAmount());
        acountAssetsRecord.setFee(BigDecimal.ZERO);
        acountAssetsRecord.setAfterBalance(balance.add(dto.getAmount()));
        acountAssetsRecord.setStatus(0);
        acountAssetsRecord.setCurrencyId(dto.getCurrencyEnum().id);
        acountAssetsRecord.setBeforeBalance(balance);
        acountAssetsRecord.setTradeType(TradeType.RECHARGE.getCode());
        accountAssetsRecordService.save(acountAssetsRecord);
    }

    public List<AccountAssets> createAccountAssets(Long accountId, Integer accountType) {
        List<AccountAssets> assetsList = new ArrayList<>();
        CurrencyEnum[] values = CurrencyEnum.values();
        for (CurrencyEnum currencyEnum : values) {
            String supportChain = currencyEnum.supportChain;
            String[] split = StringUtils.split(supportChain, ",");
            for (String s : split) {
                CurrencyEnum.CurrencyChainEnum chainEnum = CurrencyEnum.CurrencyChainEnum.findById(Integer.parseInt(s));
                AccountAssets assets = createNewAssets(accountId, accountType, currencyEnum.id, chainEnum.id);
                assetsList.add(assets);
            }
        }
        return assetsList;
    }

    private AccountAssets createNewAssets(Long accountId, Integer accountType, Integer currencyId, Integer chain) {
        AccountAssets acountAssets = new AccountAssets();
        acountAssets.setAccountId(accountId);
        acountAssets.setAccountType(accountType);
        acountAssets.setCurrencyId(currencyId);
        acountAssets.setCurrencyChain(chain);
        acountAssets.setUpdateTime(new Date());
        return acountAssets;
    }

    public void withdrawFinish(String tradeNo) {
        AccountAssetsRecord accountAssetsRecord = accountAssetsRecordService.lambdaQuery().eq(AccountAssetsRecord::getTradeNo, tradeNo).oneOpt().orElseThrow();
        accountAssetsRecord.setStatus(0);
        accountAssetsRecord.setFinishTime(new Date());
        accountAssetsRecordService.updateById(accountAssetsRecord);
        Long assetsId = accountAssetsRecord.getAssetsId();
        BigDecimal amount = accountAssetsRecord.getAmount() == null ? BigDecimal.ZERO : accountAssetsRecord.getAmount();
        BigDecimal fee = accountAssetsRecord.getFee() == null ? BigDecimal.ZERO : accountAssetsRecord.getFee();
        AccountAssets assets = accountAssetsService.getById(assetsId);
        BigDecimal freezeBalance = assets.getFreezeBalance() == null ? BigDecimal.ZERO : assets.getFreezeBalance();
        assets.setFreezeBalance(freezeBalance.subtract(amount).subtract(fee));
        assets.setUpdateTime(new Date());
        accountAssetsService.updateById(assets);
    }
}
