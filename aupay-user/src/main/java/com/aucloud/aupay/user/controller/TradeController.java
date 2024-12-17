package com.aucloud.aupay.user.controller;

import com.aucloud.aupay.user.service.TradeService;
import com.aucloud.aupay.validate.annotations.Operation;
import com.aucloud.aupay.validate.enums.OperationEnum;
import com.aucloud.aupay.validate.enums.VerifyMethod;
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
    private TradeService tradeService;

    @Operation(permission = "xxoo", operation = OperationEnum.WITHDRAW, verifyMethods = {VerifyMethod.GOOGLEAUTHENICATOR})
    @PostMapping(value = "withdraw")
    public Result<String> withdraw(@RequestBody WithdrawDTO withdrawDTO) {
        String tradeNo = tradeService.withdraw(withdrawDTO);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),tradeNo);
    }
}
