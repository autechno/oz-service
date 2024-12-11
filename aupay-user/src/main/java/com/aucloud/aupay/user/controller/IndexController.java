package com.aucloud.aupay.user.controller;

import com.aucloud.constant.ResultCodeEnum;
import com.aucloud.pojo.Result;
import com.aucloud.utils.IpUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
public class IndexController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @RequestMapping(value = "getAccessKey",method = RequestMethod.GET)
    public Result<String> getAccessKey(){
        String ipAddress = IpUtils.getIpAddress();
        Object o = redisTemplate.opsForValue().get(ipAddress + "-accessKey");
        StringBuilder key = new StringBuilder(Objects.toString(o, ""));
        if(StringUtils.isBlank(key.toString())){
            Random random = new Random();
            int keyNum = random.nextInt(90000)+10000;
            key.append(keyNum);
            for (int i = 0; i < 5; i++) {
                int ascall = random.nextInt(25) + 65;
                key.append((char) ascall);
            }
            redisTemplate.opsForValue().set(ipAddress + "-accessKey", key.toString(),1, TimeUnit.DAYS);
        }
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(), key.toString());
    }
}
