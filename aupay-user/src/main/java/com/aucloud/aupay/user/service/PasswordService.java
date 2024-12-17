package com.aucloud.aupay.user.service;

import com.aucloud.aupay.user.orm.po.AupayUser;
import com.aucloud.aupay.user.orm.service.AupayUserService;
import com.aucloud.commons.constant.EmailCodeType;
import com.aucloud.commons.constant.ResultCodeEnum;
import com.aucloud.commons.exception.ServiceRuntimeException;
import com.aucloud.commons.pojo.bo.TokenInfo;
import com.aucloud.commons.pojo.dto.ResetPasswordDTO;
import com.aucloud.commons.pojo.dto.UpdatePasswordDTO;
import com.aucloud.commons.utils.Encryption;
import com.aucloud.commons.utils.GoogleAuthenticator;
import com.aucloud.commons.utils.IpUtils;
import com.aucloud.aupay.validate.enums.OperationEnum;
import com.aucloud.aupay.validate.enums.VerifyMethod;
import com.aucloud.aupay.validate.service.CodeCheckService;
import com.aucloud.aupay.validate.service.OperationTokenService;
import com.aucloud.commons.utils.UserRequestHeaderContextHandler;
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
        TokenInfo tokenInfo = UserRequestHeaderContextHandler.getTokenInfo();
        Long userId = tokenInfo.getUserId();
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

        return operationTokenService.signToken(VerifyMethod.ASSETSPASSWORD, operationEnum, userId, IpUtils.getIpAddress());
    }

    public void updateAssetsPassword(UpdatePasswordDTO updatePasswordDTO) {
        TokenInfo tokenInfo = UserRequestHeaderContextHandler.getTokenInfo();
        Long userId = tokenInfo.getUserId();
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
        aupayUserUpd.setId(userByUsername.getId());
        aupayUserUpd.setPassword(Encryption.getSaltMD5(resetPasswordDTO.getPassword()));
        return aupayUserService.updateById(aupayUserUpd);
    }

    public void updatePassword(UpdatePasswordDTO updatePasswordDTO) {
        TokenInfo tokenInfo = UserRequestHeaderContextHandler.getTokenInfo();
        Long userId = tokenInfo.getUserId();
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
        TokenInfo tokenInfo = UserRequestHeaderContextHandler.getTokenInfo();
        Long userId = tokenInfo.getUserId();
        AupayUser userUpd = new AupayUser();
        userUpd.setId(userId);
        userUpd.setAssetsPassword(Encryption.getSaltMD5(DEFAULT_ASSETS_PASSWORD));
        boolean b = aupayUserService.updateById(userUpd);
        if (!b) {
            throw new ServiceRuntimeException(ResultCodeEnum.FAIL_TO_UPDATE.getLabel_zh_cn(), ResultCodeEnum.FAIL.getCode());
        }
    }
}
