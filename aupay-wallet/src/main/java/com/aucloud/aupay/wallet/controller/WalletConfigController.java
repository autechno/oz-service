package com.aucloud.aupay.wallet.controller;

import com.aucloud.aupay.wallet.orm.po.ConfigWalletAddress;
import com.aucloud.aupay.wallet.orm.po.ConfigWalletCollect;
import com.aucloud.aupay.wallet.orm.service.ConfigWalletAddressService;
import com.aucloud.aupay.wallet.orm.service.ConfigWalletCollectService;
import com.aucloud.constant.ResultCodeEnum;
import com.aucloud.pojo.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("config")
public class WalletConfigController {

    @Autowired
    private ConfigWalletCollectService configWalletCollectService;
    @Autowired
    private ConfigWalletAddressService configWalletAddressService;

    @GetMapping("getWalletAddress")
    public Result<List<ConfigWalletAddress>> getWalletAddress(@RequestParam(required = false) Integer walletType, @RequestParam(required = false) Integer currencyChain) {
        LambdaQueryWrapper<ConfigWalletAddress> queryWrapper = new LambdaQueryWrapper<>();
        if (walletType != null) {
            queryWrapper.eq(ConfigWalletAddress::getWalletType, walletType);
        }
        if (currencyChain != null) {
            queryWrapper.eq(ConfigWalletAddress::getCurrencyChain, currencyChain);
        }
        List<ConfigWalletAddress> list = configWalletAddressService.list(queryWrapper);
        return Result.returnResult(ResultCodeEnum.SUCCESS, list);
    }


    @GetMapping("getConfig")
    public Result<List<ConfigWalletCollect>> getConfig(@RequestParam(required = false) Integer eventType,
                                                       @RequestParam(required = false) Integer currencyId,
                                                       @RequestParam(required = false) Integer currencyChain) {
        LambdaQueryWrapper<ConfigWalletCollect> queryWrapper = new LambdaQueryWrapper<>();
        if (eventType != null) {
            queryWrapper.eq(ConfigWalletCollect::getEventType, eventType);
        }
        if (currencyId != null) {
            queryWrapper.eq(ConfigWalletCollect::getCurrencyId, currencyId);
        }
        if (currencyChain != null) {
            queryWrapper.eq(ConfigWalletCollect::getCurrencyChain, currencyChain);
        }
        List<ConfigWalletCollect> list = configWalletCollectService.list(queryWrapper);
        return Result.returnResult(ResultCodeEnum.SUCCESS, list);
    }

    @PostMapping("addConfig")
    public Result<Boolean> addConfig(@RequestBody ConfigWalletCollect configWalletCollect) {
        boolean b = configWalletCollectService.save(configWalletCollect);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),b);
    }

    @PostMapping("updateConfig")
    public Result<Boolean> updateConfig(@RequestBody ConfigWalletCollect configWalletCollect) {
        boolean b = configWalletCollectService.updateById(configWalletCollect);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),b);
    }
}
