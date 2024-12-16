package com.aucloud.aupay.validate.service;

import com.aucloud.aupay.validate.enums.OperationEnum;
import com.aucloud.aupay.validate.enums.VerifyMethod;
import com.aucloud.commons.constant.Terminal;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 签发令牌
 * 缓存至redis 有效时间5分钟/有效次数1
 * 使用IP地址、拥有者和业务类型参与进行缓存
 */
@Slf4j
public class OperationTokenService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public static String genRandomStr() {
        String s = UUID.randomUUID().toString();
        s = s.replaceAll("-", "");
        return s;
    }

    /**
     * @param operationEnum 操作
     * @param owner         所有者
     * @param ip            IP
     * @return
     */
    public String signToken(VerifyMethod verifyMethod, OperationEnum operationEnum, String owner, String ip) {
        log.info("signToken:operationEnum.name():{},verifyMethod.name():{},owner{},ip{}", operationEnum.name(), verifyMethod.name(), owner, ip);
        String token = genRandomStr();
        String key = operationEnum.name() + verifyMethod.name() + owner + ip;
        redisTemplate.opsForValue().set(key, token, 5, TimeUnit.MINUTES);
        log.info("signToken:key{},value{}", key, token);
        return token;
    }


    public Boolean verifyToken(OperationEnum operationEnum, String owner, String ip, Integer terminal) {
        if (operationEnum.terminal != null && !operationEnum.terminal.equals(terminal)) {
            return false;
        }
        if (operationEnum.permission != null && !operationEnum.permission.isEmpty() && operationEnum.permission.split(",").length > 0) {
            /*AdminDao adminDao = SpringUtils.getBean(AdminDao.class);
            int count = adminDao.checkPermission(owner,operationEnum.permission);
            if(count==0) {
                return false;
            }*/
        }
        if (operationEnum.verifyMethods != null && !operationEnum.verifyMethods.isEmpty()) {
            String[] verifyMethods = operationEnum.verifyMethods.split(",");
            for (String verify : verifyMethods) {
                VerifyMethod verifyMethod = VerifyMethod.valueOf(verify);
                //令牌验证
                if (!verifyMethod(verifyMethod, operationEnum, owner, ip, terminal)) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean verifyMethod( VerifyMethod verifyMethod, OperationEnum operationEnum, String owner, String ip, Integer terminal) {
        log.info("verifyMethod : operation name:[{}], verifyMethod name:[{}]", operationEnum.name(), verifyMethod.name());
        boolean flag = true;
        if (verifyMethod == VerifyMethod.GOOGLEAUTHENICATOR) {
//            if (Terminal.ADMIN.equals(terminal)) {
//                AdminClient adminClient = SpringUtils.getBean(AdminClient.class);
//                Result<AupayAdmin> result = adminClient.getAdminInfo(owner);
//                if (result.getCode().equals(ResultCodeEnum.SUCCESS.getCode())) {
//                    AupayAdmin data = result.getData();
//                    if (data == null || StringUtils.isBlank(data.getGoogleSecret())){
//                        logger.info("未查询到adminId:{} 绑定google验证器, 不进行google验证",owner);
//                        flag = false;
//                    }
//                } else {
//                    logger.error("判断adminId:{} 是否绑定google验证器异常。", owner);
//                    return false;
//                }
//            } else {
//                UserClient client = SpringUtils.getBean(UserClient.class);
//                Result<Boolean> booleanResult = client.haveGoogleAuth(owner);
//                if (booleanResult.getCode().equals(ResultCodeEnum.SUCCESS.getCode())) {
//                    Boolean data = booleanResult.getData();
//                    if (data == null || !data){
//                        logger.info("未查询到userId:{} 绑定google验证器, 不进行google验证",owner);
//                        flag = false;
//                    }
//                } else {
//                    logger.error("判断userId:{} 是否绑定google验证器异常。", owner);
//                    return false;
//                }
//            }
        }
        if (flag) {
            //令牌验证
            return verifyToken(verifyMethod, operationEnum, owner, ip, getToken(verifyMethod.tokenName));
        }
        return true;
    }

    public boolean verifyToken(VerifyMethod verifyMethod, OperationEnum operationEnum, String owner, String ip, String token) {
        String key = operationEnum.name() + verifyMethod.name() + owner + ip;
        Object cacheVal = redisTemplate.opsForValue().get(key);
        String source = Objects.toString(cacheVal, null);
        if (StringUtils.isNotBlank(source) && StringUtils.equals(source, token)) {
            redisTemplate.delete(key);
            return true;
        }
        log.error("verifyToken error. key:{},value:{} != token:{}", key, source, token);
        return false;
    }


    public static String getToken(String tokenName) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader(tokenName);
        if (token == null) token = "";
        return token;
    }

    public static Boolean verifyIp(Integer terminal, String ipAddress) {
        if (terminal != null && terminal.equals(Terminal.ADMIN)) {
//            AdminClient adminClient = SpringUtils.getBean(AdminClient.class);
//            int count = adminClient.checkAdminIp(ipAddress);
//            if(count==0) {
//                return false;
//            }
        }
        return true;
    }
}
