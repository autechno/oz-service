package com.aucloud.exception;

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

    public ServiceRuntimeException(String message, Integer exceptionCode) {
        super(message);
        this.exceptionCode=exceptionCode;
    }
}