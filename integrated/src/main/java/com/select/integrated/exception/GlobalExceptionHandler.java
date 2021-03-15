package com.select.integrated.exception;

import com.select.integrated.api.common.CommonResultEntity;
import com.select.integrated.api.common.CommonResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     */
    @ExceptionHandler(BizException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public CommonResultEntity customExceptionHandler(Exception e) {
        log.error("BizException 异常信息:{}", e.getMessage());
        return CommonResultEntity.failed(CommonResultEnum.BAD_REQUEST);
    }

    /**
     * 捕获RuntimeException异常
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public CommonResultEntity runtimeExceptionHandler(Exception e) {
        log.error("RuntimeException 异常信息:{}", e.getMessage());
        return CommonResultEntity.failed(e.getMessage());
    }
}
