package com.zgy.develop.rbac.advice;

import com.zgy.develop.common.utils.Result;
import com.zgy.develop.rbac.enums.ExceptionCodeEnum;
import com.zgy.develop.rbac.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 * @author zgy
 * @data 2021/5/9 18:31
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务异常
     * @param
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public Result<ExceptionCodeEnum> handleBizException(BusinessException businessException) {
        log.warn("业务异常:{}", businessException.getError().getDesc(), businessException);
        return Result.error(businessException.getError());
    }

    /**
     * 运行时异常
     * @param e
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public Result<ExceptionCodeEnum> handleRunTimeException(RuntimeException e) {
        log.warn("运行时异常: {}", e.getMessage(), e);
        return Result.error(ExceptionCodeEnum.ERROR);
    }

}
