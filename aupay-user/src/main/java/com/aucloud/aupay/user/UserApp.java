package com.aucloud.aupay.user;

import com.aucloud.aupay.security.config.AppProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableFeignClients
@EnableScheduling
@MapperScan("com.aucloud.aupay.user.orm.mapper")
@EnableConfigurationProperties(AppProperties.class)
@ComponentScan("com.aucloud.aupay")
@SpringBootApplication
public class UserApp {

    public static void main(String[] args) {
        SpringApplication.run(UserApp.class, args);
    }
}
