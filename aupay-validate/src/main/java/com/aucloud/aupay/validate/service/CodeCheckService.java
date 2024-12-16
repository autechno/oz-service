package com.aucloud.aupay.validate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class CodeCheckService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public String randomCode() {
        String s = UUID.randomUUID().toString();
        s = s.replaceAll("-","");
        Random random = new Random();
        int firstIndex = random.nextInt(s.length()-3);
        int secondIndex = random.nextInt(s.length()-3);
        String code = s.substring(secondIndex,secondIndex+3);
        code += s.substring(firstIndex, firstIndex + 3);
        return code;
    }

    public Integer getCode() {
        Random rand = new Random();
        int i = rand.nextInt(89999) + 10000;
        return i;
    }


    public Integer getMobileCode(String type, String mobile) {
        Integer valid = getCode();
        redisTemplate.opsForValue().set(mobile + type, valid+"", 10, TimeUnit.MINUTES);
        //ShortMessageUtils.sendMessage(mobile,valid+"","10");
        return valid;
    }

    public boolean checkMobileCode(String type, String mobile, String mobileCode) {
        Object o = redisTemplate.opsForValue().get(mobile + type);
        if (o != null && o.toString().equals(mobileCode)) {
            redisTemplate.delete(mobile+type);
            return true;
        }
        return false;
    }

    public Integer getEmailCode(String type, String email) {
        Integer valid = getCode();
        redisTemplate.opsForValue().set(email + type, valid+"", 10, TimeUnit.MINUTES);
        return valid;
    }

    public boolean checkEmailCode(String type,String email, String emailCode) {
        Object o = redisTemplate.opsForValue().get(email + type);
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
