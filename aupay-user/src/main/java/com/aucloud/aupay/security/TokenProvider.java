package com.aucloud.aupay.security;

import com.aucloud.aupay.validate.service.SecurityTokenHandler;
import com.aucloud.commons.pojo.bo.TokenHeadInfo;
import com.aucloud.commons.pojo.bo.TokenInfo;
import com.aucloud.commons.constant.ApplicationConstant;
import com.aucloud.commons.constant.Terminal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

//    private AppProperties appProperties;
    @Autowired
    private SecurityTokenHandler securityTokenHandler;

//    public TokenProvider(AppProperties appProperties) {
//        this.appProperties = appProperties;
//    }

    public String createToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        String userId = userPrincipal.getUserId();
        String tokenHead = TokenHeadInfo.getTokenHead(new Date(System.currentTimeMillis() + 86400000));
        String tokenInfo = TokenInfo.makeTokenInfo(Long.parseLong(userId), Terminal.USER);
        return securityTokenHandler.genToken(Long.parseLong(userId), tokenHead, tokenInfo, ApplicationConstant.SECRET);
    }

}
