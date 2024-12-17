package com.aucloud.aupay.user.service;

import com.aucloud.aupay.validate.service.GoogleAuthCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserGoogleCheckServiceImpl implements GoogleAuthCheckService {

    @Autowired
    private GoogleAuthenticatorService googleAuthenticatorService;

    @Override
    public boolean ifCheck(Long userId) {
        return !googleAuthenticatorService.haveGoogleAuth(userId);
    }
}
