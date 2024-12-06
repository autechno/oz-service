package com.aucloud.aupay.user.service;

import com.aucloud.aupay.user.orm.po.AcountAssets;
import com.aucloud.aupay.user.orm.po.AcountAssetsRecord;
import com.aucloud.aupay.user.orm.service.AcountAssetsRecordService;
import com.aucloud.aupay.user.orm.service.AcountAssetsService;
import com.aucloud.constant.ResultCodeEnum;
import com.aucloud.exception.ServiceRuntimeException;
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
                .oneOpt().orElseGet(AcountAssets::new);
        BigDecimal balance = acountAssets.getBalance();
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
        acountAssetsService.updateById(acountAssets);

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
        acountAssetsRecord.setTradeType(1);
        acountAssetsRecordService.save(acountAssetsRecord);
        return tradeNo;
    }
}
