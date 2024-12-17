package com.aucloud.aupay.validate.service;

import com.aucloud.aupay.validate.annotations.Operation;
import com.aucloud.aupay.validate.enums.OperationEnum;
import com.aucloud.aupay.validate.enums.VerifyMethod;
import com.aucloud.commons.constant.ResultCodeEnum;
import com.aucloud.commons.exception.ServiceRuntimeException;
import com.aucloud.commons.pojo.bo.TokenInfo;
import com.aucloud.commons.utils.IpUtils;
import com.aucloud.commons.utils.UserRequestHeaderContextHandler;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;

@Slf4j
@Aspect
public class OperationValidateAop {

    @Autowired
    private OperationTokenService operationTokenService;

    @Before(value = "@annotation(com.aucloud.aupay.validate.annotations.Operation)")
    public void operationVerify(JoinPoint pjp) throws NoSuchMethodException {
        String methodName = pjp.getSignature().getName();
        Class<?> targetClass = pjp.getTarget().getClass();

        Class<?>[] parameterTypes = ((MethodSignature) pjp.getSignature()).getParameterTypes();
        Method method = targetClass.getDeclaredMethod(methodName,parameterTypes);
        method.setAccessible(true);
        Operation operation = method.getAnnotation(Operation.class);

        VerifyMethod[] verifyMethods = operation.verifyMethods();
        if (verifyMethods.length == 0) {
            return;
        }

        HttpServletRequest request = getHttpServletRequest();

        String ip = IpUtils.getIpAddress();

        TokenInfo tokenInfo = UserRequestHeaderContextHandler.getTokenInfo();
        Long userId = tokenInfo.getUserId();
        OperationEnum operationEnum = operation.operation();
        for (VerifyMethod verifyMethod : verifyMethods) {
            if (verifyMethod == VerifyMethod.GOOGLEAUTHENICATOR) {
                if (operationTokenService.nonGoogle(userId)) {
                    log.info("用户没有绑定google 跳过");
                    break;
                }
            }
            String token = request.getHeader(verifyMethod.tokenName);
            boolean b = operationTokenService.verifyToken(verifyMethod, operationEnum, userId, ip, token);
            if (!b) {
                log.error("操作权限 异常：【{}】",operationEnum.operationName);
                throw new ServiceRuntimeException(ResultCodeEnum.ILLEGAL_OPERATION);
            }
        }
        Boolean verifyIp = operationTokenService.verifyIp(operationEnum.terminal, ip);
        if(!verifyIp) {
            log.error("{} be foribben.", IpUtils.getIpAddress());
            throw new ServiceRuntimeException(ResultCodeEnum.LIMITED_IP.getLabel_zh_cn(),ResultCodeEnum.LIMITED_IP.getCode());
        }
    }

    private static HttpServletRequest getHttpServletRequest() {
        HttpServletRequest request = null;
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            request = ((ServletRequestAttributes) requestAttributes).getRequest();
//            // 获取请求头信息
//            String authorizationHeader = request.getHeader("Authorization");
//            System.out.println("Authorization Header: " + authorizationHeader);
        }
        if (request == null) {
            throw new ServiceRuntimeException(ResultCodeEnum.ILLEGAL_OPERATION);
        }
        return request;
    }


}
