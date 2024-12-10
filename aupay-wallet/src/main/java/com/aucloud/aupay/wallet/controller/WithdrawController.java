package com.aucloud.aupay.wallet.controller;

import com.aucloud.aupay.wallet.orm.po.ConfigWithdraw;
import com.aucloud.aupay.wallet.orm.service.ConfigWithdrawService;
import com.aucloud.aupay.wallet.orm.service.WithdrawTaskService;
import com.aucloud.constant.ResultCodeEnum;
import com.aucloud.pojo.Result;
import com.aucloud.pojo.dto.WithdrawDTO;
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

    @GetMapping("config")
    public Result<ConfigWithdraw> config(@RequestParam Integer currencyId, @RequestParam Integer currencyChain) {
        ConfigWithdraw configWithdraw = configWithdrawService.lambdaQuery().eq(ConfigWithdraw::getCurrencyId, currencyId).eq(ConfigWithdraw::getCurrencyChain, currencyChain).oneOpt().orElseThrow();
        return Result.returnResult(ResultCodeEnum.SUCCESS, configWithdraw);
    }
}
