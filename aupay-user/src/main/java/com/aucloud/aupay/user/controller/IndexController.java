package com.aucloud.aupay.user.controller;

import com.aucloud.aupay.security.UserPrincipal;
import com.aucloud.aupay.security.token.SecurityTokenHandler;
import com.aucloud.aupay.user.orm.po.AupayUser;
import com.aucloud.aupay.user.service.LoginService;
import com.aucloud.aupay.user.service.UserRegisterService;
import com.aucloud.commons.constant.ResultCodeEnum;
import com.aucloud.commons.pojo.Result;
import com.aucloud.commons.pojo.dto.RegisterDTO;
import com.aucloud.commons.pojo.dto.UserLoginDTO;
import com.aucloud.commons.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class IndexController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private SecurityTokenHandler securityTokenHandler;
    @Autowired
    private LoginService loginService;
    @Autowired
    private UserRegisterService userRegisterService;

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

    @RequestMapping(value = "login",method = RequestMethod.POST)
//    @Operation(value = OperationEnum.USER_LOGIN,handler = DefaultOperationHandler.class,loginVerify = false)
    public Result<String> login(@RequestBody UserLoginDTO userLoginDTO) {
        String token = loginService.login(userLoginDTO);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),token);
    }

    @RequestMapping(value = "logout",method = RequestMethod.GET)
//    @Operation(value = OperationEnum.USER_LOGIN_OUT,handler = DefaultOperationHandler.class)
    public Result<?> logout() {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = principal.getId();
        securityTokenHandler.logout(id.toString());
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),null);
    }

    @RequestMapping(value = "register",method = RequestMethod.POST)
//    @Operation(value = OperationEnum.REGISTER,handler = DefaultOperationHandler.class,loginVerify = false)
    public Result<AupayUser> register(@RequestBody RegisterDTO registerDTO) {
        log.info("register. registerDTO:{}", registerDTO);
        AupayUser user = userRegisterService.register(registerDTO);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),user);
    }
}
