package com.aucloud.aupay.validate.config;

import com.aucloud.aupay.validate.service.CodeCheckService;
import com.aucloud.aupay.validate.service.GoogleAuthCheckService;
import com.aucloud.aupay.validate.service.OperationTokenService;
import com.aucloud.aupay.validate.service.SecurityTokenHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;

@AutoConfiguration
public class ValidateBeanAutoConfig {

    @Bean
    public CodeCheckService getCodeCheckService() {
        return new CodeCheckService();
    }

    @Bean
    public SecurityTokenHandler getSecurityTokenHandler(RedisTemplate<String, Object> redisTemplate) {
        return new SecurityTokenHandler(redisTemplate);
    }

    @Bean
    @ConditionalOnBean(GoogleAuthCheckService.class)
    public OperationTokenService getOperationTokenService(RedisTemplate<String, Object> redisTemplate, GoogleAuthCheckService googleAuthCheckService) {
        return new OperationTokenService(redisTemplate, googleAuthCheckService);
    }

    @Bean
    @ConditionalOnMissingBean
    public OperationTokenService getOperationTokenService(RedisTemplate<String, Object> redisTemplate) {
        return new OperationTokenService(redisTemplate);
    }
}
