package com.aucloud.pojo;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {

    private Integer code;

    private String message;

    private T data;

    private Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    private Result(Integer code) {
        this.code = code;
    }
    private Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private Result() {
    }

    public static <T> Result<T> returnResult(Integer code, String message, T data) {
        return new Result<>(code,message,data);
    }

    public static <T> Result<T> returnResult(Integer code, String message) {
        return new Result<>(code,message);
    }

    public static <T> Result<T> returnResult(Integer code) {
        return new Result<>(code);
    }

    public static <T> Result<T> error(String message, Integer exceptionCode) {
        return new Result<>(exceptionCode,message,null);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }
}

