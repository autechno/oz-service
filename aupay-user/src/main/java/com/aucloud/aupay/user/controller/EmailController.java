package com.aucloud.aupay.user.controller;

import com.aucloud.constant.ResultCodeEnum;
import com.aucloud.pojo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @RequestMapping(value = "sendEmailCode",method = RequestMethod.GET)
    @Operation(value = OperationEnum.SEND_EMAIL_CODE,handler = DefaultOperationHandler.class)
    public Result<Integer> sendEmailCode(@RequestParam Integer operationId) {
        Integer emailCode = userService.sendEmailCode(operationId);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(), emailCode);
    }

    @RequestMapping(value = "verifyEmail",method = RequestMethod.GET)
    @Operation(value = OperationEnum.VERIFY_EMAIL,handler = DefaultOperationHandler.class)
    public Result verifyEmail(@RequestParam Integer emailCode,@RequestParam Integer operationId) {
        String token = userService.verifyEmail(emailCode,operationId);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),token);
    }
}
