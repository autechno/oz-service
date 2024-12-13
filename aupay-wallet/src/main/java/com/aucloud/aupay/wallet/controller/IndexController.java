package com.aucloud.aupay.wallet.controller;

import com.aucloud.aupay.wallet.orm.po.AccountChainWallet;
import com.aucloud.aupay.wallet.orm.service.AcountChainWalletService;
import com.aucloud.commons.constant.CurrencyEnum;
import com.aucloud.commons.constant.ResultCodeEnum;
import com.aucloud.commons.pojo.Result;
import com.aucloud.commons.pojo.dto.AccountChainWalletDto;
import com.aucloud.commons.pojo.vo.RechargeInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("wallet")
public class IndexController {

    @Autowired
    private AcountChainWalletService acountChainWalletService;

    @RequestMapping(value = "getRechargeInfo",method = RequestMethod.GET)
    public Result<RechargeInfoVo> getRechargeInfo(@RequestParam Integer currencyId, @RequestParam Integer currencyChain) {
        Long accountId = 0L;
        Integer accountType = 0;
        CurrencyEnum currencyEnum = CurrencyEnum.findById(currencyId);
        AccountChainWallet accountChainWallet = acountChainWalletService.lambdaQuery()
                .eq(AccountChainWallet::getAcountId, accountId)
                .eq(AccountChainWallet::getAcountType, accountType)
                .eq(AccountChainWallet::getCurrencyChain, currencyChain)
                .oneOpt().orElseGet(()->acountChainWalletService.createAccountChainWallet(accountId, accountType, currencyChain));
        RechargeInfoVo rechargeInfo = new RechargeInfoVo();
        rechargeInfo.setAddress(accountChainWallet.getWalletAddress());
        rechargeInfo.setCurrencyChain(currencyChain);
        rechargeInfo.setCurrencyId(currencyId);
        rechargeInfo.setCofirmBlockNumber(currencyEnum.confirmBlockNumber);
        return Result.returnResult(ResultCodeEnum.SUCCESS,rechargeInfo);
    }

    @RequestMapping(value = "getAccountWallets",method = RequestMethod.GET)
    public Result<List<AccountChainWalletDto>> getAccountWallets(@RequestParam Integer accountId, @RequestParam Integer accountType) {
        List<AccountChainWallet> list = acountChainWalletService.lambdaQuery()
                .eq(AccountChainWallet::getAcountId, accountId)
                .eq(AccountChainWallet::getAcountType, accountType)
                .list();
        List<AccountChainWalletDto> collect = list.stream().map(o -> {
            AccountChainWalletDto dto = new AccountChainWalletDto();
            BeanUtils.copyProperties(o, dto);
            return dto;
        }).toList();
        return Result.returnResult(ResultCodeEnum.SUCCESS,collect);
    }

    //权限待补
//    @RequestMapping("initSysWallet")
//    public Result initSysWallet() {
//        walletService.initSysWallet();
//        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),null);
//    }

    @RequestMapping("generateAccountWallet")
    public Result<List<AccountChainWalletDto>> generateAccountWallet(@RequestParam Long accountId, @RequestParam Integer accountType) {
        List<AccountChainWallet> list = acountChainWalletService.generateAccountWallet(accountId, accountType);
        List<AccountChainWalletDto> collect = list.stream().map(o -> {
            AccountChainWalletDto dto = new AccountChainWalletDto();
            BeanUtils.copyProperties(o, dto);
            return dto;
        }).toList();
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),collect);
    }
}
