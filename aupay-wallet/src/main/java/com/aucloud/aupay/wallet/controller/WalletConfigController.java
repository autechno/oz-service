package com.aucloud.aupay.wallet.controller;

import com.aucloud.aupay.wallet.orm.po.ConfigWalletAddress;
import com.aucloud.aupay.wallet.orm.service.ConfigWalletAddressService;
import com.aucloud.constant.ResultCodeEnum;
import com.aucloud.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("config")
public class WalletConfigController {

    @Autowired
    private ConfigWalletAddressService configWalletAddressService;

    @GetMapping("getConfigedWalletAddress")
    public Result<String> getConfigedWalletAddress(@RequestParam("walletType") Integer walletType, @RequestParam("currencyChain") Integer currencyChain) {
        ConfigWalletAddress configWalletAddress = configWalletAddressService.lambdaQuery().eq(ConfigWalletAddress::getWalletType, walletType).eq(ConfigWalletAddress::getCurrencyChain, currencyChain).oneOpt().orElseThrow();
        return Result.returnResult(ResultCodeEnum.SUCCESS, configWalletAddress.getWalletAddress());
    }
}
