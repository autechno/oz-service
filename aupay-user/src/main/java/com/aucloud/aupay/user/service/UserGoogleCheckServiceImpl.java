package com.aucloud.aupay.user.service;

import com.aucloud.aupay.user.orm.po.AupayUser;
import com.aucloud.aupay.user.orm.service.AupayUserService;
import com.aucloud.aupay.validate.service.GoogleAuthCheckService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class UserGoogleCheckServiceImpl implements GoogleAuthCheckService {

    @Autowired
    private AupayUserService aupayUserService;

    @Override
    public boolean haveGoogleAuth(Long userId) {
        AupayUser userById = aupayUserService.getById(userId);
        return userById != null && StringUtils.isNotBlank(userById.getGoogleSecret());
    }
}
