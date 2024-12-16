package com.aucloud.aupay.security;

import com.aucloud.commons.constant.ApplicationConstant;
import com.aucloud.commons.constant.ResultCodeEnum;
import com.aucloud.commons.exception.ServiceRuntimeException;
import com.aucloud.commons.utils.IpUtils;
import com.aucloud.commons.utils.RequestProofManage;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class InternalListener {

    @Autowired
    private RedisTemplate redisTemplate;

    private static Logger logger = LoggerFactory.getLogger(InternalListener.class);


    @Before(value = "@annotation(com.aucloud.aupay.security.annotation.Internal)")
    public void internalProofVerify(JoinPoint pjp) {
        String methodName = pjp.getSignature().getName();
        HttpServletRequest request = (HttpServletRequest) ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String proof = request.getHeader(ApplicationConstant.AUPAY_REQUEST_PROOF);
        boolean consume = RequestProofManage.consume(proof);
        if(!consume) {
            logger.warn("proof verify fail:" + IpUtils.getIpAddress());
            throw new ServiceRuntimeException(ResultCodeEnum.ILLEGAL_OPERATION.getLabel_zh_cn(),ResultCodeEnum.ILLEGAL_OPERATION.getCode());
        }

    }


}
