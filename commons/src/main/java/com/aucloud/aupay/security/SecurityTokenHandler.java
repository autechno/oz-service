package com.aucloud.aupay.security;

import com.alibaba.fastjson2.JSONObject;
import com.aucloud.commons.constant.ApplicationConstant;
import com.aucloud.commons.constant.ResultCodeEnum;
import com.aucloud.commons.exception.ServiceRuntimeException;
import com.aucloud.aupay.security.entity.TokenInfo;
import com.aucloud.commons.utils.DateUtils;
import com.aucloud.commons.utils.SpringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;
import java.util.Set;

@Slf4j
public class SecurityTokenHandler {

    private static final String CACHE_NAME = "authentication-token-";

//    public static Boolean checkExists(String merchantId,RedisTemplate redisTemplate) {
//        ValueOperations valueOperations = redisTemplate.opsForValue();
//        HashOperations hashOperations = redisTemplate.opsForHash();
//        String hKeyName = CACHE_NAME + merchantId;
//        if(redisTemplate.hasKey(hKeyName)) {
//            Set<String> keys = hashOperations.keys(hKeyName);
//            for(String token : keys) {
//                boolean b = validationSign(token, redisTemplate);
//                if(b) {
//                    return b;
//                }
//            }
//        }
//        return false;
//    }

    public static String secret2ozbet(String jsonString) {
        return Encryption.encryptByBase64Url(jsonString);
    }

    public static String genToken(String merchantId,String jsonStringHead,String jsonStringInfo,String secret){
        RedisTemplate redisTemplate = SpringUtils.getBean(ApplicationConstant.REDIS_BEAN_NAME,RedisTemplate.class);
        ValueOperations valueOperations = redisTemplate.opsForValue();
        HashOperations hashOperations = redisTemplate.opsForHash();
        String tokenInit = Encryption.encryptByBase64Url(jsonStringHead)+"."+Encryption.encryptByBase64Url(jsonStringInfo);
        String tokenSign = Encryption.sha256_HMAC(tokenInit,secret);
        String token = tokenInit+"."+tokenSign;
        //valueOperations.set(CACHE_NAME + merchantId,token);//此用户有效token
        DataType type = redisTemplate.type(CACHE_NAME + merchantId);
        if(DataType.STRING.equals(type)) {
            redisTemplate.delete(CACHE_NAME + merchantId);
        }
        hashOperations.put(CACHE_NAME + merchantId, token, "1");
        return token;
    }

    public static boolean validationSign(String token, RedisTemplate redisTemplate){
        HashOperations hashOperations = redisTemplate.opsForHash();
        String secret = ApplicationConstant.SECRET;
        JSONObject infoObject = getInfoObject(token);
        String id = infoObject.getString("id");
        String hKeyName = CACHE_NAME + id;
        boolean valida = false;
        if(redisTemplate.hasKey(hKeyName)) {
            DataType type = redisTemplate.type(hKeyName);
            if(DataType.STRING.equals(type)) {
                redisTemplate.delete(hKeyName);
            }
        }
        if(redisTemplate.hasKey(hKeyName)) {
            Set<String> keys = hashOperations.keys(hKeyName);
            for(String o : keys) {
                if(o.equals(token)) {
                    valida = true;
                }
            }
        }
        if(!valida) {
            return valida;
        }
        try{
            int index = findIndex(token, ".", 2);
            if(index==-1){
                return false;
            }
            String tokenInit = token.substring(0, index);
            String sign = Encryption.sha256_HMAC(tokenInit, secret);
            String signParse = getSign(token);
            Object expiration = getHeadObject(token).get("expirationTime");
            long expirationTime = 0;
            long now = new Date().getTime();
            if(expiration!=null) expirationTime = DateUtils.getLongTime(expiration.toString());
            if(sign.equals(signParse) && (expiration==null || now < expirationTime)){
                return true;
            }
        }catch (Exception e){
            log.error("", e);
            return false;
        }
        return false;
    }

    public static JSONObject getHeadObject(String token){
        JSONObject headObject = null;
        try {
            String head = Encryption.decryptByBase64Url(token.substring(0, token.indexOf(".")));
            headObject = JSONObject.parseObject(head);
        }catch (Exception e){
            e.printStackTrace();
        }
        return headObject;
    }

    public static JSONObject getInfoObject(String token){
        JSONObject infoObject = null;
        try {
            String info = Encryption.decryptByBase64Url(token.substring(token.indexOf(".")+1).substring(0,token.substring(token.indexOf(".")+1).indexOf(".")));
            infoObject = JSONObject.parseObject(info);
        }catch (Exception e){
            e.printStackTrace();
        }
        return infoObject;
    }

    public static String getSign(String token){
        int index = findIndex(token, ".", 2);
        if(index==-1){
            return "";
        }
        return token.substring(index+1);
    }

    public static int findIndex(String str,String cha,int num){
        int x=0;
        for(int i=0;i<num;i++){
            x=str.indexOf(cha,x+1);
            if(x==-1){
                return x;
            }
        }
        return x;
    }

    public static String getToken(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("token");
        return token;
    }

    public static TokenInfo getTokenInfoObject(){
        RedisTemplate redisTemplate = SpringUtils.getBean(ApplicationConstant.REDIS_BEAN_NAME,RedisTemplate.class);
        String token = getToken();
        if(token==null) throw new ServiceRuntimeException("no login", ResultCodeEnum.IDENTITY_FAILURE.getCode());
        boolean b = validationSign(token, redisTemplate);
        if(!b) throw new ServiceRuntimeException("The token is invalid", ResultCodeEnum.IDENTITY_FAILURE.getCode());
        JSONObject infoObject = SecurityTokenHandler.getInfoObject(token);
        return infoObject.toJavaObject(TokenInfo.class);
    }

//    public static JSONObject getTokenInfo(){
//        RedisTemplate redisTemplate = SpringUtils.getBean(ApplicationConstant.REDIS_BEAN_NAME,RedisTemplate.class);
//        String token = getToken();
//        if(token==null) throw new ServiceRuntimeException("no login");
//        boolean b = validationSign(token, redisTemplate);
//        if(!b) throw new ServiceRuntimeException("The token is invalid", ResultCodeEnum.IDENTITY_FAILURE.getCode());
//        JSONObject infoObject = SecurityTokenHandler.getInfoObject(token);
//        return infoObject;
//    }

//    public static JSONObject getTokenHead() {
//        RedisTemplate redisTemplate = SpringUtils.getBean(ApplicationConstant.REDIS_BEAN_NAME,RedisTemplate.class);
//        String token = getToken();
//        if(token==null) throw new ServiceRuntimeException("no login", ResultCodeEnum.IDENTITY_FAILURE.getCode());
//        boolean b = validationSign(token, redisTemplate);
//        if(!b) throw new ServiceRuntimeException("The token is invalid", ResultCodeEnum.IDENTITY_FAILURE.getCode());
//        JSONObject infoObject = SecurityTokenHandler.getHeadObject(token);
//        return infoObject;
//    }

    public static void logout(String id) {
        RedisTemplate redisTemplate = SpringUtils.getBean(ApplicationConstant.REDIS_BEAN_NAME,RedisTemplate.class);
        redisTemplate.delete(CACHE_NAME + id);//使token过期
    }


}
