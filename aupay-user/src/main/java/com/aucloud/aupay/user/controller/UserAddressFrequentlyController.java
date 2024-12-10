package com.aucloud.aupay.user.controller;

import com.aucloud.aupay.user.orm.po.AccountAddressFrequently;
import com.aucloud.aupay.user.orm.service.AccountAddressFrequentlyService;
import com.aucloud.constant.ResultCodeEnum;
import com.aucloud.pojo.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("address")
public class UserAddressFrequentlyController {

    @Autowired
    private AccountAddressFrequentlyService accountAddressFrequentlyService;

    @GetMapping("getUserAddressFrequentlyList")
//    @Operation(value = OperationEnum.USER_ADDRESS_FREQUENTLY,handler = AdminOperationHandler.class)
    public Result<List<AccountAddressFrequently>> getUserAddressFrequentlyList(@RequestParam(value = "type",required = false) Integer type,
                                                                               @RequestParam(value = "currencyId",required = false) Integer currencyId){
//        String userId = SecurityTokenHandler.getTokenInfoObject().getId();
        Integer accountType = 0;
        Integer accountId = 0;
        LambdaQueryWrapper<AccountAddressFrequently> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AccountAddressFrequently::getAccountId,accountId).eq(AccountAddressFrequently::getAccountType, accountType);
        if(type != null){
            queryWrapper.eq(AccountAddressFrequently::getType,type);
        }
        if(currencyId != null){
            queryWrapper.eq(AccountAddressFrequently::getCurrencyId,currencyId);
        }
        List<AccountAddressFrequently> list = accountAddressFrequentlyService.list(queryWrapper);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(), list);
    }

    @PostMapping("saveOrUpdateUserAddressFrequently")
//    @Operation(value = OperationEnum.USER_ADDRESS_FREQUENTLY,handler = AdminOperationHandler.class)
    public Result<Boolean> saveOrUpdateUserAddressFrequently(@RequestBody AccountAddressFrequently addressFrequently){
//        String userId = SecurityTokenHandler.getTokenInfoObject().getId();
        Integer accountType = 0;
        Integer accountId = 0;
        addressFrequently.setAccountId(accountId);
        addressFrequently.setAccountType(accountType);
        boolean b = accountAddressFrequentlyService.saveOrUpdate(addressFrequently);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(), b);
    }

    @PostMapping("delUserAddressFrequently")
//    @Operation(value = OperationEnum.USER_ADDRESS_FREQUENTLY,handler = AdminOperationHandler.class)
    public Result<Boolean> delUserAddressFrequently(@RequestBody AccountAddressFrequently query){
//        String userId = SecurityTokenHandler.getTokenInfoObject().getId();
        Integer accountType = 0;
        Integer accountId = 0;
        LambdaQueryWrapper<AccountAddressFrequently> queryWrapper = new LambdaQueryWrapper<>();
        if (query.getId()!=null){
            queryWrapper = queryWrapper.eq(AccountAddressFrequently::getId, query.getId());
        }
        queryWrapper = queryWrapper.eq(AccountAddressFrequently::getAccountId, accountId)
                .eq(AccountAddressFrequently::getAccountType, accountType);
        if (query.getType() != null){
            queryWrapper = queryWrapper.eq(AccountAddressFrequently::getType, query.getType());
        }
        if (StringUtils.isNotBlank(query.getAddress())){
            queryWrapper = queryWrapper.eq(AccountAddressFrequently::getAddress, query.getAddress());
        }
        boolean b = accountAddressFrequentlyService.remove(queryWrapper);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(), b);
    }
}
