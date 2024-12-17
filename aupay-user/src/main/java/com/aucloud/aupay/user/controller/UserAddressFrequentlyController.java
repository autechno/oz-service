package com.aucloud.aupay.user.controller;

import com.aucloud.aupay.user.orm.po.AccountAddressFrequently;
import com.aucloud.aupay.user.orm.service.AccountAddressFrequentlyService;
import com.aucloud.aupay.validate.annotations.Operation;
import com.aucloud.aupay.validate.enums.OperationEnum;
import com.aucloud.aupay.validate.enums.VerifyMethod;
import com.aucloud.commons.constant.ResultCodeEnum;
import com.aucloud.commons.pojo.Result;
import com.aucloud.commons.pojo.bo.TokenInfo;
import com.aucloud.commons.utils.UserRequestHeaderContextHandler;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("address")
public class UserAddressFrequentlyController {

    @Autowired
    private AccountAddressFrequentlyService accountAddressFrequentlyService;

    @GetMapping("list")
    public Result<List<AccountAddressFrequently>> list(@RequestParam(value = "white",required = false) Boolean white,
                                                       @RequestParam(value = "currencyId",required = false) Integer currencyId,
                                                       @RequestParam(value = "currencyChain",required = false) Integer currencyChain){
        TokenInfo tokenInfo = UserRequestHeaderContextHandler.getTokenInfo();
        Integer accountType = tokenInfo.getAccountType();
        Long accountId = tokenInfo.getAccountId();
        LambdaQueryWrapper<AccountAddressFrequently> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AccountAddressFrequently::getAccountId,accountId).eq(AccountAddressFrequently::getAccountType, accountType);
        if(white != null){
            queryWrapper.eq(AccountAddressFrequently::getWhite,white);
        }
        if(currencyId != null){
            queryWrapper.eq(AccountAddressFrequently::getCurrencyId,currencyId);
        }
        if(currencyChain != null){
            queryWrapper.eq(AccountAddressFrequently::getCurrencyChain,currencyChain);
        }
        List<AccountAddressFrequently> list = accountAddressFrequentlyService.list(queryWrapper);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(), list);
    }

    @PostMapping("edit")
    @Operation(operation = OperationEnum.SWITCH_WHITE_ADDRESS, verifyMethods = {VerifyMethod.ASSETSPASSWORD, VerifyMethod.GOOGLEAUTHENICATOR})
    public Result<Boolean> saveOrUpdate(@RequestBody AccountAddressFrequently addressFrequently){
        boolean b = accountAddressFrequentlyService.saveOrUpdate(addressFrequently);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(), b);
    }

    @PostMapping("white/toggle")
    @Operation(operation = OperationEnum.SWITCH_WHITE_ADDRESS, verifyMethods = {VerifyMethod.ASSETSPASSWORD, VerifyMethod.GOOGLEAUTHENICATOR})
    public Result<Boolean> whiteToggle(@RequestParam("id") Long id){
        boolean b = accountAddressFrequentlyService.whiteToggle(id);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(), b);
    }

    @PostMapping("delete")
    @Operation(operation = OperationEnum.SWITCH_WHITE_ADDRESS, verifyMethods = {VerifyMethod.ASSETSPASSWORD, VerifyMethod.GOOGLEAUTHENICATOR})
    public Result<Boolean> delete(@RequestParam("id") Long id){
        boolean b = accountAddressFrequentlyService.deleteById(id);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(), b);
    }
}
