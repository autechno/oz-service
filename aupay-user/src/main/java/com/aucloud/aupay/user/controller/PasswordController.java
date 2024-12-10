package com.aucloud.aupay.user.controller;

import com.aucloud.constant.ResultCodeEnum;
import com.aucloud.pojo.Result;
import org.springframework.web.bind.annotation.*;

@RestController
public class PasswordController {

    @RequestMapping(value = "updateAssetsPassword",method = RequestMethod.PUT)
//    @Operation(value = OperationEnum.USER_UPDATE_ASSETS_PASSWORD,handler = DefaultOperationHandler.class)
    public Result updateAssetsPassword(@RequestBody UpdatePasswordDTO updatePasswordDTO) {
        userService.updateAssetsPassword(updatePasswordDTO);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),null);
    }


    @RequestMapping(value = "resetAssetsPassword",method = RequestMethod.PUT)
//    @Operation(value = OperationEnum.USER_RESET_ASSETS_PASSWORD,handler = DefaultOperationHandler.class)
    public Result resetAssetsPassword() {
        userService.resetAssetsPassword();
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),null);
    }

    @RequestMapping(value = "verifyAssetsPassword",method = RequestMethod.GET)
    @Operation(value = OperationEnum.VERIFY_ASSETS_PASSWORD,handler = DefaultOperationHandler.class)
    public Result verifyAssetsPassword(@RequestParam String assetsPassword, @RequestParam Integer operationId) {
        String token = userService.verifyAssetsPassword(assetsPassword,operationId);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),token);
    }

    @RequestMapping(value = "updatePassword",method = RequestMethod.PUT)
//    @Operation(value = OperationEnum.USER_UPDATE_PASSWORD,handler = DefaultOperationHandler.class)
    public Result updatePassword(@RequestBody UpdatePasswordDTO updatePasswordDTO) {
        userService.updatePassword(updatePasswordDTO);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),null);
    }

    @RequestMapping(value = "resetPassword",method = RequestMethod.PUT)
//    @Operation(value = OperationEnum.USER_RESET_PASSWORD,handler = DefaultOperationHandler.class,loginVerify = false)
    public Result resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        int count = userService.resetPassword(resetPasswordDTO);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),null);
    }
}
