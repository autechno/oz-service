package com.aucloud.aupay.user.service;

import com.aucloud.aupay.user.orm.po.AcountAssets;
import com.aucloud.aupay.user.orm.po.AcountAssetsRecord;
import com.aucloud.aupay.user.orm.service.AcountAssetsRecordService;
import com.aucloud.aupay.user.orm.service.AcountAssetsService;
import com.aucloud.constant.ResultCodeEnum;
import com.aucloud.constant.TradeType;
import com.aucloud.exception.ServiceRuntimeException;
import com.aucloud.pojo.dto.AcountRechargeDTO;
import com.aucloud.pojo.dto.WithdrawDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Service
public class AssetsService {

    @Autowired
    private AcountAssetsService acountAssetsService;
    @Autowired
    private AcountAssetsRecordService acountAssetsRecordService;

    public String preDeduct(WithdrawDTO withdrawDTO) {
        AcountAssets acountAssets = acountAssetsService.lambdaQuery()
                .eq(AcountAssets::getAcountId, withdrawDTO.getAccountId())
                .eq(AcountAssets::getAcountType, withdrawDTO.getAccountType())
                .eq(AcountAssets::getCurrencyId, withdrawDTO.getCurrencyId())
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
        AcountAssetsRecord acountAssetsRecord = new AcountAssetsRecord();
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
        AcountAssets acountAssets = acountAssetsService.lambdaQuery()
                .eq(AcountAssets::getAcountId, dto.getAccountId())
                .eq(AcountAssets::getAcountType, dto.getAccountType())
                .eq(AcountAssets::getCurrencyId, dto.getCurrencyEnum().id)
                .oneOpt().orElseGet(() -> createNewAssets(dto.getAccountId(), dto.getAccountType(), dto.getCurrencyEnum().id));
        BigDecimal balance = acountAssets.getBalance() == null ? BigDecimal.ZERO : acountAssets.getBalance();
        acountAssets.setBalance(balance.add(dto.getAmount()));
        acountAssetsService.saveOrUpdate(acountAssets);

        AcountAssetsRecord acountAssetsRecord = new AcountAssetsRecord();
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

    private AcountAssets createNewAssets(Integer accountId, Integer accountType, Integer currencyId) {
        AcountAssets acountAssets = new AcountAssets();
        acountAssets.setAcountId(accountId);
        acountAssets.setAcountType(accountType);
        acountAssets.setCurrencyId(currencyId);
        acountAssets.setUpdateTime(new Date());
        return acountAssets;
    }
}
