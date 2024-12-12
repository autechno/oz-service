package com.aucloud.commons.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.nio.charset.StandardCharsets;

//@EnableConfigurationProperties(RedisProperties.class)
@Configuration
public class RedisConfig {

//    @Autowired
//    private RedisProperties redisProperties;

//    @Bean
//    public RedisConnectionFactory redisConnectionFactory() {
//        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//        redisStandaloneConfiguration.setHostName(redisProperties.getHost());
//        redisStandaloneConfiguration.setPort(redisProperties.getPort());
//        redisStandaloneConfiguration.setPassword(redisProperties.getPassword()); // 如果没有密码，可以省略
//
//        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jedisClientConfiguration =
//                (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();
//        jedisClientConfiguration.poolConfig(jedisPoolConfig());
//
//        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration.build());
//    }
//
//    @Bean
//    public JedisPoolConfig jedisPoolConfig() {
//        JedisPoolConfig poolConfig = new JedisPoolConfig();
//        poolConfig.setMaxTotal(50); // 最大连接数
//        poolConfig.setMaxIdle(10); // 最大空闲连接
//        poolConfig.setMinIdle(2);  // 最小空闲连接
////        poolConfig.setMaxWaitMillis(1000); // 最大等待时间
//        return poolConfig;
//    }

    @Bean
    public JedisPool jedisPool(JedisConnectionFactory redisConnectionFactory) {
        GenericObjectPoolConfig<Jedis> poolConfig = redisConnectionFactory.getPoolConfig();
        RedisStandaloneConfiguration standaloneConfiguration = redisConnectionFactory.getStandaloneConfiguration();
        RedisPassword password = standaloneConfiguration.getPassword();
        String pwdString = new String(password.get());
        return new JedisPool(poolConfig, standaloneConfiguration.getHostName(), standaloneConfiguration.getPort(), 2000, pwdString);
    }

    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        StringRedisSerializer stringSerializer = new StringRedisSerializer(StandardCharsets.UTF_8);
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
