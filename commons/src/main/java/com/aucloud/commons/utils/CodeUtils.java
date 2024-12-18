package com.aucloud.commons.utils;

import com.aucloud.commons.constant.ApplicationConstant;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class CodeUtils {

    public static String randomCode() {
        String s = UUID.randomUUID().toString();
        s = s.replaceAll("-","");
        Random random = new Random();
        int firstIndex = random.nextInt(s.length()-3);
        int secondIndex = random.nextInt(s.length()-3);
        String code = s.substring(secondIndex,secondIndex+3);
        code += s.substring(firstIndex, firstIndex + 3);
        return code;
    }

    public static Integer getCode() {
        Random rand = new Random();
        int i = rand.nextInt(89999) + 10000;
        return i;
    }


    public static Integer getMobileCode(String type, String mobile) {
        RedisTemplate redisTemplate = SpringUtils.getBean(ApplicationConstant.REDIS_BEAN_NAME,RedisTemplate.class);
        Integer valid = getCode();
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(mobile + type, valid+"", 10, TimeUnit.MINUTES);
        //ShortMessageUtils.sendMessage(mobile,valid+"","10");
        return valid;
    }

    public static boolean checkMobileCode(String type, String mobile, String mobileCode) {
        RedisTemplate redisTemplate = SpringUtils.getBean(ApplicationConstant.REDIS_BEAN_NAME,RedisTemplate.class);
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Object o = valueOperations.get(mobile + type);
        if (o != null && o.toString().equals(mobileCode)) {
            redisTemplate.delete(mobile+type);
            return true;
        }
        return false;
    }

    public static Integer getEmailCode(String type, String email) {
        RedisTemplate redisTemplate = SpringUtils.getBean(ApplicationConstant.REDIS_BEAN_NAME,RedisTemplate.class);
        Integer valid = getCode();
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(email + type, valid+"", 10, TimeUnit.MINUTES);
        return valid;
    }

    public static boolean checkEmailCode(String type,String email, String emailCode) {
        RedisTemplate redisTemplate = SpringUtils.getBean(ApplicationConstant.REDIS_BEAN_NAME,RedisTemplate.class);
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Object o = valueOperations.get(email + type);
        if (o != null && o.toString().equals(emailCode)) {
            redisTemplate.delete(email+type);
            return true;
        }
        return false;
    }

    public static String randomStr() {
        String str = null;
        UUID uuid = UUID.randomUUID();
        str = uuid.toString();
        str = str.replaceAll("-","");
        return str;
    }

    public static String getInviteCode() {
        String s = UUID.randomUUID().toString();
        s = s.replaceAll("-","");
        Random random = new Random();
        int i = random.nextInt(s.length() - 5);
        return s.substring(i,i + 5);
    }

}
