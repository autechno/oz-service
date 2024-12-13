package com.aucloud.aupay.wallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class WalletApp {

    public static void main(String[] args) {
        SpringApplication.run(WalletApp.class, args);
    }
}
