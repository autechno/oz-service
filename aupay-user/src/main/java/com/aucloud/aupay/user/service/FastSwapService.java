package com.aucloud.aupay.user.service;

import com.aucloud.aupay.user.orm.po.AccountAssets;
import com.aucloud.aupay.user.orm.po.AccountAssetsRecord;
import com.aucloud.aupay.user.orm.service.AcountAssetsRecordService;
import com.aucloud.aupay.user.orm.service.AcountAssetsService;
import com.aucloud.constant.CurrencyEnum;
import com.aucloud.constant.TradeType;
import com.aucloud.exception.ServiceRuntimeException;
import com.aucloud.pojo.dto.FastSwapDTO;
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

    @Transactional
    public void fastSwap(FastSwapDTO fastSwapDTO) {
        Integer accountId = 0;
        Integer accountType = 0;
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

        if (outCurrency == null || outChain == null || inCurrency == null || inChain == null) {
            throw new ServiceRuntimeException("不支持的币种及链");
        }
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
        if (out==null || in==null) {
            throw new ServiceRuntimeException("闪兑币不能是同一种币及链");
        }

        BigDecimal totalOut = transOutAmount.add(fastSwapDTO.getFeeAmount());
        BigDecimal outBalance = out.getBalance();
        if (outBalance.compareTo(totalOut) < 0) {
            throw new ServiceRuntimeException("闪兑币不能是同一种币及链");
        }
        out.setBalance(outBalance.subtract(totalOut));
        out.setUpdateTime(new Date());
        acountAssetsService.updateById(out);

        BigDecimal inbalance = in.getBalance();
        in.setBalance(inbalance.add(fastSwapDTO.getTransInAmount()));
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
        outRecord.setTradeType(TradeType.FAST_SWAP.getCode());
        outRecord.setFee(fastSwapDTO.getFeeAmount());
        outRecord.setFinishTime(new Date());
        acountAssetsRecordService.save(outRecord);



//        String userId = SecurityTokenHandler.getTokenInfoObject().getId();
//        AupayUser user = userDao.getUserById(userId);
//        Result<List<AupayDigitalCurrencyWallet>> result = walletClient.getUserWalletByUserId(userId);
//        List<AupayDigitalCurrencyWallet> userWallets = result.getData();
//        AtomicReference<AupayDigitalCurrencyWallet> outWallet = new AtomicReference<>();
//        AtomicReference<AupayDigitalCurrencyWallet> inWallet = new AtomicReference<>();
//        userWallets.forEach(wallet -> {
//            if (wallet.getCurrencyId().equals(inCurrencyId) && wallet.getCurrencyChain().equals(inChain)) {
//                inWallet.set(wallet);
//            }
//            if (wallet.getCurrencyId().equals(outCurrencyId) && wallet.getCurrencyChain().equals(outChain)) {
//                outWallet.set(wallet);
//            }
//        });

//        log.info("outWallet:{}", JSON.toJSONString(outWallet.get()));
//        log.info("inWallet:{}", JSON.toJSONString(inWallet.get()));

//        if (outWallet.get().getBalance().compareTo(transOutAmount)<0) {
//            //余额不足
//            return;
//        }
        /*BigDecimal swapFee = transOutAmount.multiply(BigDecimal.ZERO);//闪兑手续费
        //汇率转换
        AtomicReference<BigDecimal> inRate = new AtomicReference<>(BigDecimal.ZERO);
        AtomicReference<BigDecimal> outRate = new AtomicReference<>(BigDecimal.ZERO);
        String inSymbol = getSymbol(fastSwapDTO.getInCurrencyId(), fastSwapDTO.getInChain());
        String outSymbol = getSymbol(fastSwapDTO.getOutCurrencyId(), fastSwapDTO.getOutChain());
        if (StringUtils.equalsIgnoreCase(inSymbol,"usdt")) {
            inRate.set(BigDecimal.ONE);
        } else if (StringUtils.equalsIgnoreCase(inSymbol,"ozc")) {
            inRate.set(BigDecimal.ONE);
        } else if (StringUtils.equalsIgnoreCase(inSymbol,"toto")) {
            inRate.set(BigDecimal.ONE);
        }
        if (StringUtils.equalsIgnoreCase(outSymbol,"usdt")) {
            outRate.set(BigDecimal.ONE);
        } else if (StringUtils.equalsIgnoreCase(outSymbol,"ozc")) {
            outRate.set(BigDecimal.ONE);
        } else if (StringUtils.equalsIgnoreCase(outSymbol,"toto")) {
            outRate.set(BigDecimal.ONE);
        }
        if (outRate.get().equals(BigDecimal.ZERO) || inRate.get().equals(BigDecimal.ZERO)) {
            Result<List<HuobiTicker>> result1 = sysClient.getUSDTTickers();
            List<HuobiTicker> usdtTickers = result1.getData();
            usdtTickers.forEach(huobiTicker -> {
                BigDecimal bid = huobiTicker.getBid();
                if (inRate.get().equals(BigDecimal.ZERO)) {
                    if (huobiTicker.getSymbol().equalsIgnoreCase(inSymbol)) {
                        inRate.set(bid);
                    }
                }
                if (outRate.get().equals(BigDecimal.ZERO)) {
                    if (huobiTicker.getSymbol().equalsIgnoreCase(outSymbol)) {
                        outRate.set(bid);
                    }
                }
            });
        }
        if (outRate.get().equals(BigDecimal.ZERO) || inRate.get().equals(BigDecimal.ZERO)) {
            throw new ServiceRuntimeException("币种转换异常");
        }
        BigDecimal rate = inRate.get().divide(outRate.get(), 4, RoundingMode.HALF_UP);

        BigDecimal transInAmount = transOutAmount.subtract(swapFee).multiply(rate);//汇率转换*/



//        AupayUserAssetsChangeRecord outAssetsChangeRecord = userAssetsChange(userId, outCurrencyId, outChain, transOutAmount.negate(), null, TradeType.FAST_SWAP, null, null);
//        walletClient.addBalance(outWallet.get().getWalletId().toString(), transOutAmount.negate());
//        AupayUserAssetsChangeRecord inAssetsChangeRecord = userAssetsChange(userId, inCurrencyId, inChain, transInAmount, null, TradeType.FAST_SWAP, null, null);
//        walletClient.addBalance(inWallet.get().getWalletId().toString(), transInAmount);

    }
}
