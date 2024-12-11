package com.aucloud.exception;

import com.aucloud.constant.ResultCodeEnum;
import com.aucloud.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class AupayExceptionHandler {

//    private static final String REQUEUST_ID = "requestId";
//    /**
//     * 每个request生成唯一id
//     * @param model
//     */
//    @ModelAttribute
//    public void addAttributes(Model model) {
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        request.setAttribute(REQUEUST_ID, IdGenUtil.getUUID());
//    }

//    @ExceptionHandler(MysqlDataTruncation.class)
//    public Result handleMysqlDataTruncation(MysqlDataTruncation e) {
//        log.error("error:{}",e.getLocalizedMessage(), e);
//        return Result.error(ResultCodeEnum.DATA_FORMART.getLabel_zh_cn(), ResultCodeEnum.DATA_FORMART.getCode());
//    }

    @ExceptionHandler(DuplicateKeyException.class)
    public Result<?> handleMySQLIntegrityConstraintViolationException(DuplicateKeyException e) {
        log.error("error:{}",e.getLocalizedMessage(), e);
        return Result.error(ResultCodeEnum.DUPLICATE_KEY.getLabel_zh_cn(), ResultCodeEnum.DUPLICATE_KEY.getCode());
    }


    @ExceptionHandler(RuntimeException.class)
    public Result<?> handleRunTimeException(RuntimeException e) {
        log.error("error:{}",e.getLocalizedMessage(), e);
        if(e.getCause() instanceof ServiceRuntimeException) {
            return handleException((ServiceRuntimeException) e.getCause());
        }
        return Result.error(ResultCodeEnum.UNKNOW.getLabel_zh_cn(),ResultCodeEnum.UNKNOW.getCode());
    }

    @ExceptionHandler(ServiceRuntimeException.class)
    public Result<?> handleException(ServiceRuntimeException e) {
        log.error("error:{}",e.getLocalizedMessage(), e);
        return Result.error(e.getMessage(),e.getExceptionCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("error:{}",e.getLocalizedMessage(), e);
        String message = "未知参数错误";
        BindingResult result = e.getBindingResult();
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            for(ObjectError objectError : errors) {
                FieldError fieldError = (FieldError) objectError;

                message =  fieldError.getField() + fieldError.getDefaultMessage();
            }
        }
        return Result.error(message,400);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("error:{}",e.getLocalizedMessage(), e);
        String message = "参数缺失";
        return Result.error(message,400);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Result<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("error:{}",e.getLocalizedMessage(), e);
        String message = "参数类型异常";
        return Result.error(message,400);
    }

}
