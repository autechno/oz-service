package com.aucloud.aupay.trade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TradeApp {
    public static void main(String[] args) {
        SpringApplication.run(TradeApp.class, args);
    }
}