package com.aucloud.commons.utils;

import com.aucloud.commons.constant.ApplicationConstant;
import com.aucloud.commons.constant.BooleanIntegerMapping;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Collections;

public class RequestProofManage {

    private static final Long SIGN_EXPIR_TIME_S = 30L;

    private static final Long RESERVE_EXPIR_TIME_S = 86400L;

    /**
     * 签发请求凭证 有效期30秒
     * @return
     */
    public static String sign() {
        String proof = CodeUtils.randomStr();
        RedisTemplate redisTemplate = SpringUtils.getBean(ApplicationConstant.REDIS_BEAN_NAME,RedisTemplate.class);
        DefaultRedisScript defaultRedisScript = new DefaultRedisScript();
        defaultRedisScript.setScriptText("if redis.call('setnx', KEYS[1],ARGV[1]) == 1 then redis.call('expire',KEYS[1],ARGV[2]) return 1 else return 0 end");
        defaultRedisScript.setResultType(Long.class);
        Long result = (Long) redisTemplate.execute(defaultRedisScript, Collections.singletonList(proof), BooleanIntegerMapping.FALSE.toString() ,SIGN_EXPIR_TIME_S.toString());
        if(result == null || result != 1){
            throw new RuntimeException("内部错误transaction错误");
        }
        return proof;
    }

    /**
     * 使用
     * 仅可消费一次 进行状态变成  有效期延长为48h
     */
    public static boolean consume(String proof) {
        RedisTemplate redisTemplate = SpringUtils.getBean(ApplicationConstant.REDIS_BEAN_NAME,RedisTemplate.class);
        DefaultRedisScript defaultRedisScript = new DefaultRedisScript();
        defaultRedisScript.setScriptText("if redis.call('get', KEYS[1]) == ARGV[1] then " +
                "redis.call('set', KEYS[1], ARGV[2]) " +
                "redis.call('expire',KEYS[1],ARGV[4]) return 1 " +
                "else return 0 end");
        defaultRedisScript.setResultType(Long.class);
        Long result = (Long) redisTemplate.execute(defaultRedisScript, Collections.singletonList(proof), BooleanIntegerMapping.FALSE.toString(), BooleanIntegerMapping.TRUE.toString(), "OK",RESERVE_EXPIR_TIME_S.toString());
        if(result == null || result != 1){
            return false;
        }
        return true;
    }



}
