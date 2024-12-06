package com.aucloud.aupay.wallet.controller;

import com.aucloud.aupay.constant.MerchantsConstant;
import com.aucloud.aupay.constant.ResultCodeEnum;
import com.aucloud.aupay.feign.AdminClient;
import com.aucloud.aupay.pojo.Result;
import com.aucloud.aupay.pojo.bo.AupayDigitalCurrencyWallet;
import com.aucloud.aupay.pojo.do_.AupayApplication;
import com.aucloud.aupay.security.annotation.ApiSignVerify;
import com.aucloud.aupay.service.WalletService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("trade")
public class TradeController {

    @Autowired
    private WalletService walletService;
    @Autowired
    private AdminClient adminClient;

    /**
     * 获取用户钱包信息
     * @return
     */
    @RequestMapping("getUserWallet")
    @ApiSignVerify
    public Result<AupayDigitalCurrencyWallet> getUserWallet(@RequestParam String userId, @RequestParam(name = "currencyId", required = false) Integer currencyId, @RequestParam(name = "currencyChain", required = false) Integer currencyChain) {
        AupayDigitalCurrencyWallet userDigitalCurrencyWallet = walletService.getUserDigitalCurrencyWallet(userId, currencyId, currencyChain);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(), userDigitalCurrencyWallet);
    }

    /**
     * 获取应用钱包信息
     * @return
     */
    @RequestMapping("getApplicationWalletByApplicationId")
    public Result getApplicationWalletByApplicationId(@RequestParam String applicationId) {
        List<AupayDigitalCurrencyWallet> list = walletService.getApplicationWalletByApplicationId(applicationId);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(), list);
    }


//    @RequestMapping("getUserWalletByUserId")
//    public Result<List<AupayDigitalCurrencyWallet>> getUserWalletByUserId(@RequestParam  String userId) {
//        List<AupayDigitalCurrencyWallet> list = walletService.getUserWalletByUserId(userId);
//        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),list);
//    }

    //权限待补
    @RequestMapping("initApplinctionWallet")
    public Result<?> initApplinctionWallet(@RequestParam String applicationId) {
        if(StringUtils.isBlank(applicationId)) {
            Result<AupayApplication> applicationResult = adminClient.getApplicationInfoByMerId(MerchantsConstant.MERCHANT_OZBET);
            if (applicationResult == null || applicationResult.getCode() != ResultCodeEnum.SUCCESS.getCode()) {
                return Result.returnResult(ResultCodeEnum.NOT_FOUND.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),null);
            }
            applicationId = applicationResult.getData().getApplicationId();
        }
        walletService.initApplinctionWallet(applicationId);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),null);
    }
}
