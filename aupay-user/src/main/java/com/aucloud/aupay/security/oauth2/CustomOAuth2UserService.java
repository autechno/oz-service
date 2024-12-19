package com.aucloud.aupay.security.oauth2;

import com.aucloud.aupay.security.UserPrincipal;
import com.aucloud.aupay.security.oauth2.user.OAuth2UserInfo;
import com.aucloud.aupay.security.oauth2.user.OAuth2UserInfoFactory;
import com.aucloud.aupay.user.orm.po.AupayUser;
import com.aucloud.aupay.user.orm.service.AupayUserService;
import com.aucloud.aupay.user.service.UserRegisterService;
import com.aucloud.commons.constant.AuthProvider;
import com.aucloud.aupay.security.OAuth2AuthenticationProcessingException;
import com.aucloud.commons.pojo.dto.RegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private AupayUserService aupayUserService;
    @Autowired
    private UserRegisterService userRegisterService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }

        Optional<AupayUser> userOptional = aupayUserService.lambdaQuery().eq(AupayUser::getEmail,oAuth2UserInfo.getEmail()).oneOpt();
        AupayUser user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            if (!user.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                        user.getProvider() + " account. Please use your " + user.getProvider() +
                        " account to login.");
            }
            user = updateExistingUser(user, oAuth2UserInfo);
        } else {
            user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }

        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }

    private AupayUser registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        RegisterDTO registerDTO = new RegisterDTO();

        registerDTO.setIsBindPay(1);
        registerDTO.setProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()).name());
        registerDTO.setProviderId(oAuth2UserInfo.getId());
        registerDTO.setUsername(oAuth2UserInfo.getName());
        registerDTO.setEmail(oAuth2UserInfo.getEmail());
        registerDTO.setHeadPortrait(oAuth2UserInfo.getImageUrl());
        return userRegisterService.register(registerDTO);
    }

    private AupayUser updateExistingUser(AupayUser existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setUsername(oAuth2UserInfo.getName());
        existingUser.setHeadPortrait(oAuth2UserInfo.getImageUrl());
        aupayUserService.saveOrUpdate(existingUser);
        return existingUser;
    }

}
