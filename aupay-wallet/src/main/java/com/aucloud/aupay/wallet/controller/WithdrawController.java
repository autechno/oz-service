package com.aucloud.aupay.wallet.controller;

import com.aucloud.aupay.wallet.orm.service.WithdrawTaskService;
import com.aucloud.constant.ResultCodeEnum;
import com.aucloud.pojo.Result;
import com.aucloud.pojo.dto.WithdrawDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("withdraw")
public class WithdrawController {

    @Autowired
    private WithdrawTaskService withdrawTaskService;

    @PostMapping("generateWithdrawTask")
    public Result<?> generateWithdrawTask(@RequestBody WithdrawDTO withdrawDTO) {
        withdrawTaskService.saveWithdrawTask(withdrawDTO);
        return Result.returnResult(ResultCodeEnum.SUCCESS);
    }
}
