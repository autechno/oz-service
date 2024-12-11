package com.aucloud.aupay.user.controller;

import com.aucloud.aupay.user.orm.po.AupayUser;
import com.aucloud.aupay.user.orm.service.AupayUserService;
import com.aucloud.aupay.user.service.EmailService;
import com.aucloud.constant.ResultCodeEnum;
import com.aucloud.exception.ServiceRuntimeException;
import com.aucloud.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("email")
public class EmailController {

    @Autowired
    private EmailService emailService;
    @Autowired
    private AupayUserService aupayUserService;

    @RequestMapping(value = "sendEmailCode", method = RequestMethod.GET)
//    @Operation(value = OperationEnum.SEND_EMAIL_CODE,handler = DefaultOperationHandler.class)
    public Result<Integer> sendEmailCode(@RequestParam Integer operationId) {
        Integer emailCode = emailService.sendEmailCode(operationId);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(), emailCode);
    }

    @RequestMapping(value = "verifyEmail", method = RequestMethod.GET)
//    @Operation(value = OperationEnum.VERIFY_EMAIL,handler = DefaultOperationHandler.class)
    public Result<String> verifyEmail(@RequestParam Integer emailCode, @RequestParam Integer operationId) {
        String token = emailService.verifyEmail(emailCode, operationId);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(), token);
    }

    @RequestMapping(value = "getUserBlurEmail", method = RequestMethod.GET)
    public Result<?> getUserBlurEmail(@RequestParam String username) {
        AupayUser userInfoByUsername = aupayUserService.lambdaQuery().eq(AupayUser::getUsername, username).oneOpt().orElseThrow(() -> new ServiceRuntimeException(ResultCodeEnum.USER_NOT_EXISTS));
        String blurEmail = userInfoByUsername.getEmail().replaceAll("^(.{3})(.*@)(.*)$", "$1*****@$3");
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(), blurEmail);
    }

    @RequestMapping(value = "sendResetPasswordEmailCode", method = RequestMethod.GET)
//    @Operation(value = OperationEnum.SEND_RESET_PASSWORD_EMAIL_CODE,handler = DefaultOperationHandler.class,loginVerify = false)
    public Result<?> sendResetPasswordEmailCode(@RequestParam String username) {
        Integer integer = emailService.sendResetPasswordEmailCode(username);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(), integer);
    }

    @RequestMapping(value = "sendRegisterEmailCode", method = RequestMethod.GET)
    public Result<?> sendRegisterEmailCode(@RequestParam String email) {
        emailService.sendRegisterEmailCode(email);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(), null);
    }

    @RequestMapping(value = "sendWithdrawEmailCode", method = RequestMethod.GET)
    public Result<?> sendWithdrawEmailCode() {
        emailService.sendWithdrawEmailCode();
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(), null);
    }

    @RequestMapping(value = "sendLoginEmailCode", method = RequestMethod.GET)
    public Result<?> sendLoginEmailCode(@RequestParam String username) {
        emailService.sendLoginEmailCode(username);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(), null);
    }
}
