package com.aucloud.aupay.validate.config;

import com.aucloud.aupay.validate.service.CodeCheckService;
import com.aucloud.aupay.validate.service.OperationTokenService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class ValidateBeanAutoConfig {

    @Bean
    public OperationTokenService getOperationTokenService() {
        return new OperationTokenService();
    }

    @Bean
    public CodeCheckService getCodeCheckService() {
        return new CodeCheckService();
    }
}
