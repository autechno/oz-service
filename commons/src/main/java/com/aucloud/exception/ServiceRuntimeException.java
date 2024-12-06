package com.aucloud.exception;

import com.aucloud.constant.ResultCodeEnum;
import lombok.Getter;

@Getter
public class ServiceRuntimeException extends RuntimeException {

    /**
     * 异常状态码
     */
    private Integer exceptionCode;

    public ServiceRuntimeException(String message) {
        super(message);
    }
    public ServiceRuntimeException(ResultCodeEnum codeEnum) {
        this(codeEnum.getLabel_zh_cn(), codeEnum.getCode());
    }

    public ServiceRuntimeException(String message, Integer exceptionCode) {
        super(message);
        this.exceptionCode=exceptionCode;
    }
}