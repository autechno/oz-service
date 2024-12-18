package com.aucloud.aupay.user.config;

import com.aucloud.aupay.user.service.UserGoogleCheckServiceImpl;
import com.aucloud.aupay.validate.config.ValidateBeanAutoConfig;
import com.aucloud.aupay.validate.service.GoogleAuthCheckService;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureBefore(ValidateBeanAutoConfig.class)
public class GoogleCheckConfig {

    @Bean
    public GoogleAuthCheckService googleAuthCheckService() {
        return new UserGoogleCheckServiceImpl();
    }
}
