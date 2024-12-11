package com.aucloud.aupay.user.controller;

import com.aucloud.aupay.user.service.PasswordService;
import com.aucloud.constant.ResultCodeEnum;
import com.aucloud.pojo.Result;
import com.aucloud.pojo.dto.ResetPasswordDTO;
import com.aucloud.pojo.dto.UpdatePasswordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PasswordController {

    @Autowired
    private PasswordService passwordService;

    @RequestMapping(value = "updateAssetsPassword",method = RequestMethod.PUT)
//    @Operation(value = OperationEnum.USER_UPDATE_ASSETS_PASSWORD,handler = DefaultOperationHandler.class)
    public Result<?> updateAssetsPassword(@RequestBody UpdatePasswordDTO updatePasswordDTO) {
        passwordService.updateAssetsPassword(updatePasswordDTO);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),null);
    }


    @RequestMapping(value = "resetAssetsPassword",method = RequestMethod.PUT)
//    @Operation(value = OperationEnum.USER_RESET_ASSETS_PASSWORD,handler = DefaultOperationHandler.class)
    public Result<?> resetAssetsPassword() {
        passwordService.resetAssetsPassword();
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),null);
    }

    @RequestMapping(value = "verifyAssetsPassword",method = RequestMethod.GET)
//    @Operation(value = OperationEnum.VERIFY_ASSETS_PASSWORD,handler = DefaultOperationHandler.class)
    public Result<String> verifyAssetsPassword(@RequestParam String assetsPassword, @RequestParam Integer operationId) {
        String token = passwordService.verifyAssetsPassword(assetsPassword,operationId);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),token);
    }

    @RequestMapping(value = "updatePassword",method = RequestMethod.PUT)
//    @Operation(value = OperationEnum.USER_UPDATE_PASSWORD,handler = DefaultOperationHandler.class)
    public Result<?> updatePassword(@RequestBody UpdatePasswordDTO updatePasswordDTO) {
        passwordService.updatePassword(updatePasswordDTO);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),null);
    }

    @RequestMapping(value = "resetPassword",method = RequestMethod.PUT)
//    @Operation(value = OperationEnum.USER_RESET_PASSWORD,handler = DefaultOperationHandler.class,loginVerify = false)
    public Result<Boolean> resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        boolean b = passwordService.resetPassword(resetPasswordDTO);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),b);
    }
}
