package com.aucloud.aupay.trade.controller;

import com.aucloud.aupay.trade.service.WithdrawService;
import com.aucloud.commons.constant.ResultCodeEnum;
import com.aucloud.commons.pojo.Result;
import com.aucloud.commons.pojo.dto.WithdrawDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class TradeController {

    @Autowired
    private WithdrawService withdrawService;

    @PostMapping(value = "withdraw")
//    @Operation(value = OperationEnum.WITHDRAW,handler = DefaultOperationHandler.class)
    public Result<String> withdraw(@RequestBody WithdrawDTO withdrawDTO) {
        String tradeNo = withdrawService.withdraw(withdrawDTO);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),tradeNo);
    }
}
