package com.aucloud.aupay.user.controller;

import com.aucloud.aupay.user.service.AssetsService;
import com.aucloud.constant.ResultCodeEnum;
import com.aucloud.pojo.Result;
import com.aucloud.pojo.dto.WithdrawDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("assets")
public class AssetsController {

    @Autowired
    private AssetsService assetsService;

    @PostMapping("pre-deduct")
    public Result<String> preDeduct(@RequestBody WithdrawDTO withdrawDTO) {
        String tradeNo = assetsService.preDeduct(withdrawDTO);
        return Result.returnResult(ResultCodeEnum.SUCCESS, tradeNo);
    }
}
