package com.aucloud.aupay.wallet;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@MapperScan("com.aucloud.aupay.wallet.orm.mapper")
@SpringBootApplication
@EnableFeignClients
public class WalletApp {

    public static void main(String[] args) {
        SpringApplication.run(WalletApp.class, args);
    }
}
