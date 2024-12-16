package com.aucloud.aupay.security;

import com.aucloud.commons.constant.ResultCodeEnum;
import com.aucloud.aupay.encryption.MD5;
import com.aucloud.commons.exception.ServiceRuntimeException;
import com.aucloud.aupay.feign.AdminClient;
import com.aucloud.aupay.feign.SysClient;
import com.aucloud.commons.pojo.Result;
import com.aucloud.aupay.security.annotation.ApiSignVerify;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class ApiSignVerifyListener {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SysClient sysClient;

    @Autowired
    private AdminClient adminClient;

    private static Logger logger = LoggerFactory.getLogger(ApiSignVerifyListener.class);

    private static final String APPLICATION_PARAMTER_NAME = "applicationId";

    private static final String SIGN_PARAMTER_NAME = "sign";

    private static final String API_KEY_SIGN_NAME = "APIKEY";
    /**
     * 找出参数中商户号
     * 根据参数进行签名验证
     * @param pjp
     */
    @Before(value = "@annotation(com.aucloud.aupay.security.annotation.ApiSignVerify)")
    public void internalProofVerify(JoinPoint pjp) {
        String methodName = pjp.getSignature().getName();
        Class<?> targetClass = pjp.getTarget().getClass();
        HttpServletRequest request = (HttpServletRequest) ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Class[] parameterTypes = ((MethodSignature) pjp.getSignature()).getParameterTypes();
        Class<RequestParam> requestParamClass = RequestParam.class;
        Method method = null;
        String applicationId = null;
        String sign = null;
        StringBuffer sourceBuffer = new StringBuffer();
        try {
            method = targetClass.getDeclaredMethod(methodName, parameterTypes);
            if (!method.isAnnotationPresent(ApiSignVerify.class)) {
// 如果方法没有@ApiSignVerify注解，则跳过验证
                return;
            }
            method.setAccessible(true);
            Parameter[] parameters = method.getParameters();
            List<Parameter> parametersList = Arrays.asList(parameters);
            parametersList.sort((parameter1, parameter2) -> {
                String parameterName1 = parameter1.getAnnotation(requestParamClass).value();
                String parameterName2 = parameter2.getAnnotation(requestParamClass).value();
                return parameterName1.compareTo(parameterName2);
            });
            for (Parameter parameter : parametersList) {
                RequestParam annotation = parameter.getAnnotation(requestParamClass);
                String parameterName = annotation.value();
                if (StringUtils.isEmpty(parameterName)) {
                    parameterName = parameter.getName();
                }
                String parameterValue = request.getParameter(parameterName);
                if (APPLICATION_PARAMTER_NAME.equals(parameterName)) {
                    applicationId = parameterValue;
                }
                if (!SIGN_PARAMTER_NAME.equals(parameterName)) {
                    sourceBuffer.append(parameterName + "=" + parameterValue + "&");
                }
            }
            sign = request.getParameter(SIGN_PARAMTER_NAME);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        Result applicationApiKeyResult = adminClient.getApplicationApiKey(applicationId);
        Object data = applicationApiKeyResult.getData();
        String apikey = data != null ? data.toString() : null;
        if (apikey == null) {
            throw new ServiceRuntimeException(ResultCodeEnum.FAIL_TO_VERIFY.getLabel_zh_cn(), ResultCodeEnum.FAIL_TO_VERIFY.getCode());
        }
        sourceBuffer.append(API_KEY_SIGN_NAME + "=" + apikey);
        String source = sourceBuffer.toString();
        System.out.println(source);
        String signVerifier = MD5.md5(source);
        System.out.println(signVerifier);
        if (!signVerifier.equals(sign)) {
            throw new ServiceRuntimeException(ResultCodeEnum.FAIL_TO_VERIFY.getLabel_zh_cn(), ResultCodeEnum.FAIL_TO_VERIFY.getCode());
        }
    }


    /**
     * 找出参数中商户号
     * 根据参数进行签名验证
     * @param pjp
     */
//    @Before(value = "@annotation(com.aucloud.aupay.security.annotation.ApiSignVerify)")
//    public void internalProofVerify(JoinPoint pjp) {
//        String methodName = pjp.getSignature().getName();
//        Class<?> targetClass = pjp.getTarget().getClass();
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        Class[] parameterTypes = ((MethodSignature) pjp.getSignature()).getParameterTypes();
//        Class<RequestParam> requestParamClass = RequestParam.class;
//        Method method = null;
//        String applicationId = null;
//        String sign = null;
//        StringBuffer sourceBuffer = new StringBuffer();
//        try {
//            method = targetClass.getDeclaredMethod(methodName,parameterTypes);
//            method.setAccessible(true);
//            Parameter[] parameters = method.getParameters();
//            //遍历找出商户号 并且拼接加密参数
//            List<Parameter> parametersList = Arrays.asList(parameters);
//            parametersList.sort((parameter1,parameter2) -> {
//                String parameterName1 = parameter1.getAnnotation(requestParamClass).value();
//                String parameterName2 = parameter2.getAnnotation(requestParamClass).value();
//                return parameterName1.compareTo(parameterName2);
//            });
//            for (Parameter parameter : parametersList) {
//                //加密要使用传参参数名 而不是方法名
//                RequestParam annotation = parameter.getAnnotation(requestParamClass);
//                String parameterName = annotation.value();
//                if(StringUtils.isEmpty(parameterName)) {
//                    parameterName = parameter.getName();
//                }
//                String parameterValue = request.getParameter(parameterName);
//                if(APPLICATION_PARAMTER_NAME.equals(parameterName)) {
//                    applicationId = parameterValue;
//                }
//                if(!SIGN_PARAMTER_NAME.equals(parameterName)) {
//                    sourceBuffer.append(parameterName + "=" + parameterValue + "&");
//                }
//            }
//            sign = request.getParameter(SIGN_PARAMTER_NAME);
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//        //获取商户apikey
//        Result applicationApiKeyResult = sysClient.getApplicationApiKey(applicationId);
//        Object data = applicationApiKeyResult.getData();
//        String apikey = data!= null?data.toString():null;
//        if(apikey == null) {
//            throw new ServiceRuntimeException(ResultCodeEnum.FAIL_TO_VERIFY.getLabel_zh_cn(),ResultCodeEnum.FAIL_TO_VERIFY.getCode());
//        }
//        sourceBuffer.append(API_KEY_SIGN_NAME + "=" + apikey);
//        String source = sourceBuffer.toString();
//        System.out.println(source);
//        String signVerifier = MD5.md5(source);
//        System.out.println(signVerifier);
//        if(!signVerifier.equals(sign)) {
//            throw new ServiceRuntimeException(ResultCodeEnum.FAIL_TO_VERIFY.getLabel_zh_cn(),ResultCodeEnum.FAIL_TO_VERIFY.getCode());
//        }
//
//    }

}

