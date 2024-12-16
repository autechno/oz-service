package com.aucloud.aupay.operate;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@EnableScheduling
@MapperScan("com.aucloud.aupay.operate.orm.mapper")
@SpringBootApplication
public class OperateApp {
    public static void main(String[] args) {
        SpringApplication.run(OperateApp.class, args);
    }

//    @Bean
//    public RestTemplate restTemplate() {
//        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
//        connectionManager.setMaxTotal(200);  // 设置连接池最大连接数
//        connectionManager.setDefaultMaxPerRoute(20); // 每个路由最大连接数
//
//        HttpClientBuilder httpClientBuilder = HttpClients.custom()
//                .setConnectionManager(connectionManager)
//                .setDefaultRequestConfig(RequestConfig.custom()
//                        .setConnectionRequestTimeout(5, TimeUnit.SECONDS) // 设置连接超时
//                        .setResponseTimeout(5, TimeUnit.SECONDS)  // 设置读取超时
//                        .build());
//        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClientBuilder.build());
//        return new RestTemplate(factory);
//    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .rootUri("https://api.example.com") // 可设置默认的根URI
                .setConnectTimeout(Duration.ofSeconds(5))  // 设置连接超时（毫秒）
                .setReadTimeout(Duration.ofSeconds(5))     // 设置读取超时（毫秒）
                .build();
    }
}