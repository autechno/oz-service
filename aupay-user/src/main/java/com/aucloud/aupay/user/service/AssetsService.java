package com.aucloud.aupay.user.service;

import com.aucloud.aupay.user.orm.po.AccountAssets;
import com.aucloud.aupay.user.orm.po.AccountAssetsRecord;
import com.aucloud.aupay.user.orm.service.AcountAssetsRecordService;
import com.aucloud.aupay.user.orm.service.AcountAssetsService;
import com.aucloud.commons.constant.ResultCodeEnum;
import com.aucloud.commons.constant.TradeType;
import com.aucloud.commons.exception.ServiceRuntimeException;
import com.aucloud.commons.pojo.PageQuery;
import com.aucloud.commons.pojo.dto.AccountAssetsRecordQuery;
import com.aucloud.commons.pojo.dto.AcountRechargeDTO;
import com.aucloud.commons.pojo.dto.WithdrawDTO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class AssetsService {

    @Autowired
    private AcountAssetsService acountAssetsService;
    @Autowired
    private AcountAssetsRecordService acountAssetsRecordService;

    public List<AccountAssets> getAccountAssets(Integer accountId, Integer accountType) {
        return acountAssetsService.lambdaQuery()
                .eq(AccountAssets::getAccountId, accountId)
                .eq(AccountAssets::getAccountType, accountType)
                .list();
    }

    public Page<AccountAssetsRecord> getAssetsRecords(PageQuery<AccountAssetsRecordQuery> pageQuery) {
        AccountAssetsRecordQuery conditions = pageQuery.getConditions();
        Page<AccountAssetsRecord> page = new Page<>(pageQuery.getPageNo(), pageQuery.getPageSize());
        return acountAssetsRecordService.getBaseMapper().getAssetsRecords(page, conditions);
    }

    public String preDeduct(WithdrawDTO withdrawDTO) {
        AccountAssets acountAssets = acountAssetsService.lambdaQuery()
                .eq(AccountAssets::getAccountId, withdrawDTO.getAccountId())
                .eq(AccountAssets::getAccountType, withdrawDTO.getAccountType())
                .eq(AccountAssets::getCurrencyId, withdrawDTO.getCurrencyId())
                .oneOpt().orElseGet(() -> createNewAssets(withdrawDTO.getAccountId(), withdrawDTO.getAccountType(), withdrawDTO.getCurrencyId()));
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
        acountAssetsService.saveOrUpdate(acountAssets);

        String tradeNo = UUID.randomUUID().toString();
        AccountAssetsRecord acountAssetsRecord = new AccountAssetsRecord();
        acountAssetsRecord.setAssetsId(acountAssets.getId());
        acountAssetsRecord.setTradeNo(tradeNo);
        acountAssetsRecord.setAmount(amount);
        acountAssetsRecord.setFee(fee);
        acountAssetsRecord.setAfterBalance(balance.subtract(deduct));
        acountAssetsRecord.setStatus(0);
        acountAssetsRecord.setCurrencyId(withdrawDTO.getCurrencyId());
        acountAssetsRecord.setBeforeBalance(balance);
        acountAssetsRecord.setTradeType(TradeType.WITHDRAW.getCode());
        acountAssetsRecordService.save(acountAssetsRecord);
        return tradeNo;
    }

    public void recharge(AcountRechargeDTO dto) {
        AccountAssets acountAssets = acountAssetsService.lambdaQuery()
                .eq(AccountAssets::getAccountId, dto.getAccountId())
                .eq(AccountAssets::getAccountType, dto.getAccountType())
                .eq(AccountAssets::getCurrencyId, dto.getCurrencyEnum().id)
                .oneOpt().orElseGet(() -> createNewAssets(dto.getAccountId(), dto.getAccountType(), dto.getCurrencyEnum().id));
        BigDecimal balance = acountAssets.getBalance() == null ? BigDecimal.ZERO : acountAssets.getBalance();
        acountAssets.setBalance(balance.add(dto.getAmount()));
        acountAssetsService.saveOrUpdate(acountAssets);

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
        acountAssetsRecordService.save(acountAssetsRecord);
    }

    private AccountAssets createNewAssets(Integer accountId, Integer accountType, Integer currencyId) {
        AccountAssets acountAssets = new AccountAssets();
        acountAssets.setAccountId(accountId);
        acountAssets.setAccountType(accountType);
        acountAssets.setCurrencyId(currencyId);
        acountAssets.setUpdateTime(new Date());
        return acountAssets;
    }

    public void withdrawFinish(String tradeNo) {
        AccountAssetsRecord acountAssetsRecord = acountAssetsRecordService.lambdaQuery().eq(AccountAssetsRecord::getTradeNo, tradeNo).oneOpt().orElseThrow();
        acountAssetsRecord.setStatus(0);
        acountAssetsRecord.setFinishTime(new Date());
        acountAssetsRecordService.updateById(acountAssetsRecord);
        Integer assetsId = acountAssetsRecord.getAssetsId();
        BigDecimal amount = acountAssetsRecord.getAmount() == null ? BigDecimal.ZERO : acountAssetsRecord.getAmount();
        BigDecimal fee = acountAssetsRecord.getFee() == null ? BigDecimal.ZERO : acountAssetsRecord.getFee();
        AccountAssets assets = acountAssetsService.getById(assetsId);
        BigDecimal freezeBalance = assets.getFreezeBalance() == null ? BigDecimal.ZERO : assets.getFreezeBalance();
        assets.setFreezeBalance(freezeBalance.subtract(amount).subtract(fee));
        assets.setUpdateTime(new Date());
        acountAssetsService.updateById(assets);
    }
}
