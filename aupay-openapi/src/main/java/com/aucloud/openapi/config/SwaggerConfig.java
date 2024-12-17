package com.aucloud.openapi.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    // 配置分组 API
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
            .group("aupay")  // 设置 API 文档分组名称
            .pathsToMatch("/api/**")  // 只生成 /api/** 路径下的 API 文档
            .build();
    }

    // 配置 OpenAPI 文档的基本信息
    @Bean
    public Info apiInfo() {
        return new Info()
            .title("AuPay API Documentation")
            .description("API Documentation for AuPay Application")
            .version("1.0")
            .contact(new io.swagger.v3.oas.models.info.Contact()
                .name("AuCloud OpenAPI")
                .email("dev@autech.one"));
    }
}
