package com.aucloud.aupay.user.service;

import com.aucloud.aupay.user.orm.po.AupayUser;
import com.aucloud.aupay.user.orm.service.AupayUserService;
import com.aucloud.constant.EmailCodeType;
import com.aucloud.constant.ResultCodeEnum;
import com.aucloud.exception.ServiceRuntimeException;
import com.aucloud.pojo.dto.ResetPasswordDTO;
import com.aucloud.pojo.dto.UpdatePasswordDTO;
import com.aucloud.utils.Encryption;
import com.aucloud.utils.GoogleAuthenticator;
import com.aucloud.utils.IpUtils;
import com.aucloud.validate.enums.OperationEnum;
import com.aucloud.validate.enums.VerifyMethod;
import com.aucloud.validate.service.CodeCheckService;
import com.aucloud.validate.service.OperationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {

    private static final String DEFAULT_ASSETS_PASSWORD = "000000";

    @Autowired
    private AupayUserService aupayUserService;
    @Autowired
    private OperationTokenService operationTokenService;
    @Autowired
    private CodeCheckService codeCheckService;

    public String verifyAssetsPassword(String assetsPassword, Integer operationId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = 0L;
        return verifyAssetsPassword(userId, assetsPassword, operationId);
    }

    public String verifyAssetsPassword(Long userId, String assetsPassword, Integer operationId) {
        AupayUser user = aupayUserService.getById(userId);
        String assetsPasswordMD5 = user.getAssetsPassword();
        boolean saltverifyMD5 = Encryption.getSaltverifyMD5(assetsPassword, assetsPasswordMD5);
        if (!saltverifyMD5) {
            throw new ServiceRuntimeException(ResultCodeEnum.FAIL_TO_PASSWORD.getLabel_zh_cn(), ResultCodeEnum.FAIL_TO_PASSWORD.getCode());
        }
        OperationEnum operationEnum = OperationEnum.getById(operationId);

        return operationTokenService.signToken(VerifyMethod.ASSETSPASSWORD, operationEnum, String.valueOf(userId), IpUtils.getIpAddress());
    }

    public void updateAssetsPassword(UpdatePasswordDTO updatePasswordDTO) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = 0L;
        AupayUser aupayUserUpd = new AupayUser();
        aupayUserUpd.setId(userId);
        aupayUserUpd.setAssetsPassword(Encryption.getSaltMD5(updatePasswordDTO.getNewPassword()));
        boolean b = aupayUserService.updateById(aupayUserUpd);
        if (!b) {
            throw new ServiceRuntimeException(ResultCodeEnum.FAIL_TO_UPDATE.getLabel_zh_cn(), ResultCodeEnum.FAIL.getCode());
        }
    }

    public boolean resetPassword(ResetPasswordDTO resetPasswordDTO) {
        AupayUser userByUsername = aupayUserService.lambdaQuery().eq(AupayUser::getUsername, resetPasswordDTO.getUsername()).one();
        String email = userByUsername.getEmail();
        boolean b = codeCheckService.checkEmailCode(EmailCodeType.RESET_PASSWORD, email, resetPasswordDTO.getEmailCode());
        if (!b) {
            throw new ServiceRuntimeException(ResultCodeEnum.FAIL_TO_VERIFY.getLabel_zh_cn(), ResultCodeEnum.FAIL_TO_VERIFY.getCode());
        }
        if (resetPasswordDTO.getGoogleCode()!=null&&resetPasswordDTO.getGoogleCode()>0){
            GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
            boolean b1 = googleAuthenticator.check_code(userByUsername.getGoogleSecret(), resetPasswordDTO.getGoogleCode(), System.currentTimeMillis());
            if (!b1) {
                throw new ServiceRuntimeException(ResultCodeEnum.FAIL_TO_VERIFY.getLabel_zh_cn(), ResultCodeEnum.FAIL_TO_VERIFY.getCode());
            }
        }
        AupayUser aupayUserUpd = new AupayUser();
        aupayUserUpd.setUserId(userByUsername.getUserId());
        aupayUserUpd.setPassword(Encryption.getSaltMD5(resetPasswordDTO.getPassword()));
        return aupayUserService.updateById(aupayUserUpd);
    }

    public void updatePassword(UpdatePasswordDTO updatePasswordDTO) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = 0L;
        AupayUser userById = aupayUserService.getById(userId);
        if (!Encryption.getSaltverifyMD5(updatePasswordDTO.getOldPassword(), userById.getPassword())) {
            throw new ServiceRuntimeException(ResultCodeEnum.FAIL_TO_VERIFY.getLabel_zh_cn(), ResultCodeEnum.FAIL_TO_VERIFY.getCode());
        }
        AupayUser userUpd = new AupayUser();
        userUpd.setId(userId);
        userUpd.setPassword(Encryption.getSaltMD5(updatePasswordDTO.getNewPassword()));
        boolean b = aupayUserService.updateById(userUpd);
        if (!b) {
            throw new ServiceRuntimeException(ResultCodeEnum.FAIL_TO_UPDATE.getLabel_zh_cn(), ResultCodeEnum.FAIL.getCode());
        }
    }

    public void resetAssetsPassword() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = 0L;
        AupayUser userUpd = new AupayUser();
        userUpd.setId(userId);
        userUpd.setAssetsPassword(Encryption.getSaltMD5(DEFAULT_ASSETS_PASSWORD));
        boolean b = aupayUserService.updateById(userUpd);
        if (!b) {
            throw new ServiceRuntimeException(ResultCodeEnum.FAIL_TO_UPDATE.getLabel_zh_cn(), ResultCodeEnum.FAIL.getCode());
        }
    }
}
