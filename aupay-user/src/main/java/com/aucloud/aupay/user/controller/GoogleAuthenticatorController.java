package com.aucloud.aupay.user.controller;

import com.aucloud.aupay.user.orm.service.AupayUserService;
import com.aucloud.aupay.user.service.GoogleAuthenticatorService;
import com.aucloud.constant.ResultCodeEnum;
import com.aucloud.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GoogleAuthenticatorController {

    @Autowired
    private GoogleAuthenticatorService googleAuthenticatorService;

    @RequestMapping(value = "haveGoogleAuth", method = RequestMethod.GET)
//    @Operation(value = OperationEnum.GET_USER_INFO,handler = DefaultOperationHandler.class)
    public Result<Boolean> haveGoogleAuth() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = 0L;
        boolean b = googleAuthenticatorService.haveGoogleAuth(userId);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(), b);
    }

    @RequestMapping(value = "bindGoogleAuth", method = RequestMethod.GET)
//    @Operation(value = OperationEnum.USER_BIND_GOOGLE_AUTH,handler = DefaultOperationHandler.class)
    public Result<Map<String, String>> bindGoogleAuth() {
        Map<String, String> map = googleAuthenticatorService.bindGoogleAuth();
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(), map);
    }

    @RequestMapping(value = "bindGoogleAuth", method = RequestMethod.PUT)
//    @Operation(value = OperationEnum.USER_BIND_GOOGLE_AUTH,handler = DefaultOperationHandler.class)
    public Result<Boolean> bindGoogleAuth(@RequestParam(name = "googleSecret") String googleSecret, @RequestParam(name = "googleCode") Long googleCode) {
        googleAuthenticatorService.bindGoogleAuth(googleSecret, googleCode);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(), true);
    }

    @RequestMapping(value = "resetGoogleAuth", method = RequestMethod.PUT)
//    @Operation(value = OperationEnum.USER_RESET_GOOGLE_AUTH,handler = DefaultOperationHandler.class)
    public Result<?> resetGoogleAuth() {
        googleAuthenticatorService.resetGoogleAuth();
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(), null);
    }

    @RequestMapping(value = "verifyGoogle", method = RequestMethod.GET)
//    @Operation(value = OperationEnum.USER_VERIFY_GOOGLE_AUTH,handler = DefaultOperationHandler.class)
    public Result<String> verifyGoogle(@RequestParam Long googleCode, @RequestParam Integer operationId) {
        String token = googleAuthenticatorService.verifyGoogle(googleCode, operationId);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(), token);
    }
}
