package com.aucloud.aupay.validate.service;

import com.alibaba.fastjson2.JSONObject;
import com.aucloud.commons.constant.ApplicationConstant;
import com.aucloud.commons.constant.ResultCodeEnum;
import com.aucloud.commons.exception.ServiceRuntimeException;
import com.aucloud.commons.pojo.bo.TokenInfo;
import com.aucloud.commons.utils.Encryption;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;

@Slf4j
public class SecurityTokenHandler {

    private static final String CACHE_NAME = "aupay-authentication-token-";

    private final RedisTemplate<String, Object> redisTemplate;

    public SecurityTokenHandler(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String secret2ozbet(String jsonString) {
        return Encryption.encryptByBase64Url(jsonString);
    }

    public String genToken(Long id,String jsonStringHead,String jsonStringInfo,String secret){
        String tokenInit = Encryption.encryptByBase64Url(jsonStringHead)+"."+Encryption.encryptByBase64Url(jsonStringInfo);
        String tokenSign = Encryption.sha256_HMAC(tokenInit,secret);
        String token = tokenInit+"."+tokenSign;
        redisTemplate.opsForValue().set(CACHE_NAME + id,token);//此用户有效token
        return token;
    }

    public boolean validationSign(String token){
        String secret = ApplicationConstant.SECRET;
        JSONObject infoObject = getInfoObject(token);
        String id = infoObject.getString("id");
        String hKeyName = CACHE_NAME + id;
        Object cachedToken = redisTemplate.opsForValue().get(hKeyName);
        if(cachedToken == null || !cachedToken.toString().equals(token)){
            return false;
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
            if(expiration!=null) {
                Date date = DateUtils.parseDate(expiration.toString(), "yyyy-MM-dd HH:mm:ss");
                expirationTime = date.getTime();
            }
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
            log.error("", e);
        }
        return headObject;
    }

    public static JSONObject getInfoObject(String token){
        JSONObject infoObject = null;
        try {
            String info = Encryption.decryptByBase64Url(token.substring(token.indexOf(".")+1).substring(0,token.substring(token.indexOf(".")+1).indexOf(".")));
            infoObject = JSONObject.parseObject(info);
        }catch (Exception e){
            log.error("", e);
        }
        return infoObject;
    }

    public String getSign(String token){
        int index = findIndex(token, ".", 2);
        if(index==-1){
            return "";
        }
        return token.substring(index+1);
    }

    public int findIndex(String str,String cha,int num){
        int x=0;
        for(int i=0;i<num;i++){
            x=str.indexOf(cha,x+1);
            if(x==-1){
                return x;
            }
        }
        return x;
    }

    public TokenInfo getTokenInfoObject(String token){
        if(token==null) {
            throw new ServiceRuntimeException("no login", ResultCodeEnum.IDENTITY_FAILURE.getCode());
        }
        boolean b = validationSign(token);
        if(!b) {
            throw new ServiceRuntimeException("The token is invalid", ResultCodeEnum.IDENTITY_FAILURE.getCode());
        }
        JSONObject infoObject = SecurityTokenHandler.getInfoObject(token);
        return infoObject.toJavaObject(TokenInfo.class);
    }

    public void logout(String id) {
        redisTemplate.delete(CACHE_NAME + id);//使token过期
    }


}
