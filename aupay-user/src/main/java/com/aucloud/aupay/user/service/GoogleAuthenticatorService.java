package com.aucloud.aupay.user.service;

import com.aucloud.aupay.user.orm.po.AupayUser;
import com.aucloud.aupay.user.orm.service.AupayUserService;
import com.aucloud.commons.constant.ResultCodeEnum;
import com.aucloud.commons.exception.ServiceRuntimeException;
import com.aucloud.commons.utils.GoogleAuthenticator;
import com.aucloud.commons.utils.IpUtils;
import com.aucloud.aupay.validate.enums.OperationEnum;
import com.aucloud.aupay.validate.enums.VerifyMethod;
import com.aucloud.aupay.validate.service.OperationTokenService;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class GoogleAuthenticatorService {

    @Autowired
    private AupayUserService aupayUserService;
    @Autowired
    private OperationTokenService operationTokenService;

    public boolean haveGoogleAuth(Long userId) {
        AupayUser userById = aupayUserService.getById(userId);
        return userById != null && StringUtils.isNotBlank(userById.getGoogleSecret());
    }

    public Map<String, String> bindGoogleAuth() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = 0L;
        AupayUser userById = aupayUserService.getById(userId);
        if (StringUtils.isNotBlank(userById.getGoogleSecret())) {
            throw new ServiceRuntimeException("已经绑定过谷歌验证", ResultCodeEnum.FAIL.getCode());
        }
        String secretkey = GoogleAuthenticator.generateSecretKey();
        String qr = GoogleAuthenticator.genQR(userById.getUsername(), "aupay.one", secretkey);
        Map<String, String> map = new HashMap<>();
        map.put("qr", qr);
        map.put("googleSecret", secretkey);
        return map;
    }

    @Transactional
    public void bindGoogleAuth(String googleSecret, Long googleCode) {
        GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
        googleAuthenticator.setWindowSize(1);
        boolean b = googleAuthenticator.check_code(googleSecret, googleCode, System.currentTimeMillis());
        if (!b) {
            throw new ServiceRuntimeException(ResultCodeEnum.FAIL_TO_VERIFY.getLabel_zh_cn(), ResultCodeEnum.FAIL_TO_VERIFY.getCode());
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = 0L;

        AupayUser aupayUser = new AupayUser();
        aupayUser.setId(userId);
        aupayUser.setGoogleSecret(googleSecret);
        boolean b1 = aupayUserService.updateById(aupayUser);
        if (!b1) {
            throw new ServiceRuntimeException(ResultCodeEnum.FAIL_TO_UPDATE.getLabel_zh_cn(), ResultCodeEnum.FAIL.getCode());
        }
    }

    public void resetGoogleAuth() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = 0L;
        AupayUser user = aupayUserService.getById(userId);

        LambdaUpdateWrapper<AupayUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(AupayUser::getId, user.getId());
        updateWrapper.set(AupayUser::getGoogleSecret, null);
        boolean b = aupayUserService.update(updateWrapper);
        if (!b) {
            throw new ServiceRuntimeException(ResultCodeEnum.FAIL_TO_UPDATE.getLabel_zh_cn(), ResultCodeEnum.FAIL.getCode());
        }
    }

    public String verifyGoogle(Long googleCode, Integer operationId) {
        OperationEnum operationEnum = OperationEnum.getById(operationId);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = 0L;
        AupayUser userById = aupayUserService.getById(userId);
        GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
        googleAuthenticator.setWindowSize(1);
        boolean b = googleAuthenticator.check_code(userById.getGoogleSecret(), googleCode, System.currentTimeMillis());
        if (!b) {
            throw new ServiceRuntimeException(ResultCodeEnum.FAIL_TO_VERIFY.getLabel_zh_cn(), ResultCodeEnum.FAIL_TO_VERIFY.getCode());
        }
        return operationTokenService.signToken(VerifyMethod.GOOGLEAUTHENICATOR, operationEnum, userId, IpUtils.getIpAddress());
    }
}
