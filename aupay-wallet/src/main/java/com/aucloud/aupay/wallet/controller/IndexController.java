package com.aucloud.aupay.wallet.controller;

import com.aucloud.aupay.constant.ResultCodeEnum;
import com.aucloud.aupay.pojo.Result;
import com.aucloud.aupay.pojo.vo.RechargeInfoVo;
import com.aucloud.aupay.security.SecurityTokenHandler;
import com.aucloud.aupay.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class IndexController {

    @Autowired
    private WalletService walletService;

    @RequestMapping(value = "getRechargeInfo",method = RequestMethod.GET)
    public Result getRechargeInfo(@RequestParam Integer currencyId, @RequestParam Integer currencyChain) {
        String userId = SecurityTokenHandler.getTokenInfoObject().getId();
        RechargeInfoVo rechargeInfo = walletService.getUserRechargeInfo(userId,currencyId,currencyChain);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),rechargeInfo);
    }



}
