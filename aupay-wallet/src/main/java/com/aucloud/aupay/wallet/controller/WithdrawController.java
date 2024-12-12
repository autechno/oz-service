package com.aucloud.aupay.wallet.controller;

import com.aucloud.aupay.wallet.orm.po.ConfigWithdraw;
import com.aucloud.aupay.wallet.orm.service.ConfigWithdrawService;
import com.aucloud.aupay.wallet.orm.service.WithdrawTaskService;
import com.aucloud.commons.constant.ResultCodeEnum;
import com.aucloud.commons.pojo.Result;
import com.aucloud.commons.pojo.dto.WithdrawDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("withdraw")
public class WithdrawController {

    @Autowired
    private WithdrawTaskService withdrawTaskService;
    @Autowired
    private ConfigWithdrawService configWithdrawService;

    @PostMapping("generateWithdrawTask")
    public Result<?> generateWithdrawTask(@RequestBody WithdrawDTO withdrawDTO) {
        withdrawTaskService.saveWithdrawTask(withdrawDTO);
        return Result.returnResult(ResultCodeEnum.SUCCESS);
    }

    @GetMapping("getConfig")
    public Result<ConfigWithdraw> getConfig(@RequestParam Integer currencyId, @RequestParam Integer currencyChain) {
        ConfigWithdraw configWithdraw = configWithdrawService.lambdaQuery().eq(ConfigWithdraw::getCurrencyId, currencyId).eq(ConfigWithdraw::getCurrencyChain, currencyChain).oneOpt().orElseThrow();
        return Result.returnResult(ResultCodeEnum.SUCCESS, configWithdraw);
    }

    @PostMapping("addConfig")
    public Result<Boolean> addConfig(@RequestBody ConfigWithdraw configWithdraw) {
        boolean b = configWithdrawService.save(configWithdraw);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),b);
    }

    @PostMapping("updateConfig")
    public Result<Boolean> updateConfig(@RequestBody ConfigWithdraw configWithdraw) {
        boolean b = configWithdrawService.updateById(configWithdraw);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),b);
    }
}
