package com.aucloud.aupay.user.service;

import com.aucloud.constant.ResultCodeEnum;
import com.aucloud.exception.ServiceRuntimeException;
import com.aucloud.utils.GoogleAuthenticator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class GoogleAuthenticatorService {


    public Map<String,String> bindGoogleAuth() {
        String userId = SecurityTokenHandler.getTokenInfoObject().getId();
        AupayUser userById = userDao.getUserById(userId);
        if (!StringUtils.isEmpty(userById.getGoogleSecret())) {
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
    public void bindGoogleAuth(String googleSecret,Long googleCode) {
        GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
        googleAuthenticator.setWindowSize(1);
        boolean b = googleAuthenticator.check_code(googleSecret, googleCode, System.currentTimeMillis());
        if (!b) {
            throw new ServiceRuntimeException(ResultCodeEnum.FAIL_TO_VERIFY.getLabel_zh_cn(), ResultCodeEnum.FAIL_TO_VERIFY.getCode());
        }

        String userId = SecurityTokenHandler.getTokenInfoObject().getId();
        AupayUser aupayUser = new AupayUser();
        aupayUser.setUserId(userId);
        aupayUser.setGoogleSecret(googleSecret);
        int i = userDao.updateUser(aupayUser);
        if (i == 0) {
            throw new ServiceRuntimeException(ResultCodeEnum.FAIL_TO_UPDATE.getLabel_zh_cn(), ResultCodeEnum.FAIL.getCode());
        }
    }
}
