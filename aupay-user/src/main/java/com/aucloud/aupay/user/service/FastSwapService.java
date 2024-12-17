package com.aucloud.aupay.user.service;

import com.aucloud.aupay.db.orm.po.FastSwapRecord;
import com.aucloud.aupay.user.feign.FeignWalletService;
import com.aucloud.aupay.user.orm.po.AccountAssets;
import com.aucloud.aupay.user.orm.po.AccountAssetsRecord;
import com.aucloud.aupay.user.orm.service.AcountAssetsRecordService;
import com.aucloud.aupay.user.orm.service.AcountAssetsService;
import com.aucloud.commons.constant.CurrencyEnum;
import com.aucloud.commons.constant.ResultCodeEnum;
import com.aucloud.commons.constant.TradeType;
import com.aucloud.commons.exception.ServiceRuntimeException;
import com.aucloud.commons.pojo.Result;
import com.aucloud.commons.pojo.dto.FastSwapDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class FastSwapService {

    @Autowired
    private AcountAssetsService acountAssetsService;
    @Autowired
    private AcountAssetsRecordService acountAssetsRecordService;
    @Autowired
    private FeignWalletService feignWalletService;

    @Transactional
    public void fastSwap(FastSwapDTO fastSwapDTO) {
        Long accountId = fastSwapDTO.getAccountId();
        Integer accountType = fastSwapDTO.getAccountType();
        BigDecimal transOutAmount = fastSwapDTO.getTransOutAmount();
        BigDecimal transInAmount = fastSwapDTO.getTransInAmount();

        Integer outCurrencyId = fastSwapDTO.getOutCurrencyId();
        Integer outCurrencyChain = fastSwapDTO.getOutChain();
        CurrencyEnum outCurrency = CurrencyEnum.findById(outCurrencyId);
        CurrencyEnum.CurrencyChainEnum outChain = CurrencyEnum.CurrencyChainEnum.findById(outCurrencyChain);

        Integer inCurrencyId = fastSwapDTO.getInCurrencyId();
        Integer inCurrencyChain = fastSwapDTO.getInChain();
        CurrencyEnum inCurrency = CurrencyEnum.findById(inCurrencyId);
        CurrencyEnum.CurrencyChainEnum inChain = CurrencyEnum.CurrencyChainEnum.findById(inCurrencyChain);

        if (outCurrency==inCurrency && outChain == inChain) {
            throw new ServiceRuntimeException("闪兑币不能是同一种币及链");
        }

        log.info("outCurrencyId:{} outChain:{} -> inCurrencyId:{} inChain:{} transOutAmount:{} -> transInAmount:{}", outCurrencyId,outChain,inCurrencyId,inChain, transOutAmount, transInAmount);

        List<AccountAssets> list = acountAssetsService.lambdaQuery().eq(AccountAssets::getAccountId, accountId).eq(AccountAssets::getAccountType, accountType).list();

        AccountAssets in = null, out = null;
        for (AccountAssets accountAssets : list) {
            Integer currencyId = accountAssets.getCurrencyId();
            Integer currencyChain = accountAssets.getCurrencyChain();
            if (currencyId.equals(outCurrencyId) && currencyChain.equals(outCurrencyChain)) {
                out = accountAssets;
            } else if (currencyId.equals(inCurrencyId) && currencyChain.equals(inCurrencyChain)) {
                in = accountAssets;
            }
        }
        if (out==null) {
            throw new ServiceRuntimeException(ResultCodeEnum.INSUFFICIENT_BALANCE);
        }

        BigDecimal totalOut = transOutAmount.add(fastSwapDTO.getFeeAmount());
        BigDecimal outBalance = out.getBalance();
        if (outBalance.compareTo(totalOut) < 0) {
            throw new ServiceRuntimeException(ResultCodeEnum.INSUFFICIENT_BALANCE);
        }
        if (in == null) {
            in = new AccountAssets();
            in.setBalance(BigDecimal.ZERO);
            in.setUpdateTime(new Date());
            in.setFreezeBalance(BigDecimal.ZERO);
            in.setAccountId(accountId);
            in.setAccountType(accountType);
            in.setCurrencyChain(inCurrencyId);
            in.setCurrencyChain(inCurrencyChain);
            acountAssetsService.save(in);
        }

        //fast_swap_record表
        FastSwapRecord record = new FastSwapRecord();
        record.setAccountId(accountId);
        record.setAccountType(accountType);
        record.setOperatorId(fastSwapDTO.getEmployeeId());
        record.setCashOutAssetsId(out.getId());
        record.setCashOutCurrencyId(outCurrencyId);
        record.setCashOutChain(outCurrencyChain);
        record.setCashOutAmount(transOutAmount);
        record.setCashInAssetsId(in.getId());
        record.setCashInCurrencyId(inCurrencyId);
        record.setCashInChain(inCurrencyChain);
        record.setCashInAmount(transInAmount);
        record.setFee(fastSwapDTO.getFeeAmount());
        record.setStatus(0);
        Result<String> result = feignWalletService.generateFastSwapRecord(record);
        String tradeNo = result.getData();

        out.setBalance(outBalance.subtract(totalOut));
        out.setUpdateTime(new Date());
        acountAssetsService.updateById(out);

        BigDecimal inbalance = in.getBalance();
        in.setBalance(inbalance.add(transInAmount));
        in.setUpdateTime(new Date());
        acountAssetsService.updateById(in);

        AccountAssetsRecord inRecord = new AccountAssetsRecord();
        inRecord.setAssetsId(in.getId());
        inRecord.setCurrencyChain(in.getCurrencyChain());
        inRecord.setCurrencyId(in.getCurrencyId());
        inRecord.setAmount(transInAmount);
        inRecord.setAfterBalance(in.getBalance());
        inRecord.setBeforeBalance(inbalance);
        inRecord.setStatus(0);
        inRecord.setTradeType(TradeType.FAST_SWAP.getCode());
        inRecord.setTradeNo(tradeNo);
        inRecord.setFinishTime(new Date());
        acountAssetsRecordService.save(inRecord);
        AccountAssetsRecord outRecord = new AccountAssetsRecord();
        outRecord.setAssetsId(out.getId());
        outRecord.setCurrencyChain(out.getCurrencyChain());
        outRecord.setCurrencyId(out.getCurrencyId());
        outRecord.setAmount(transOutAmount);
        outRecord.setAfterBalance(out.getBalance());
        outRecord.setBeforeBalance(outBalance);
        outRecord.setStatus(0);
        outRecord.setTradeNo(tradeNo);
        outRecord.setTradeType(TradeType.FAST_SWAP.getCode());
        outRecord.setFee(fastSwapDTO.getFeeAmount());
        outRecord.setFinishTime(new Date());
        acountAssetsRecordService.save(outRecord);
    }
}
