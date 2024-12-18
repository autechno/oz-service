package com.aucloud.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class AsyncConfig {

    @Value("${task.executor.core-pool-size:5}")
    private int corePoolSize; // 如果未配置，则默认为5

    @Value("${task.executor.max-pool-size:10}")
    private int maxPoolSize; // 如果未配置，则默认为10

    @Value("${task.executor.queue-capacity:25}")
    private int queueCapacity; // 如果未配置，则默认为25

    @Bean(name = AppConfigConstants.TASK_EXECUTOR_BEAN_NAME)
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("eventTaskExecutor-");
        executor.initialize();
        return executor;
    }
}