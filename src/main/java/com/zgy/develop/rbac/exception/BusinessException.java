package com.zgy.develop.rbac.exception;

import com.zgy.develop.rbac.enums.ExceptionCodeEnum;

/**
 * 自定义业务异常
 * @author zgy
 * @data 2021/5/9 18:29
 */

public class BusinessException extends RuntimeException{

    private ExceptionCodeEnum error;

    /**
     * 有时我们需要将第三方异常转为自定义异常抛出，同时又不想丢失原来的异常信息，此时可以传入cause
     * @param error
     * @param cause
     */
    public BusinessException(ExceptionCodeEnum error, Throwable cause) {
        super(cause);
        this.error = error;
    }

    /**
     * 传入通用错误枚举
     * @param error
     */
    public BusinessException(ExceptionCodeEnum error) {
        this.error = error;
    }

    public ExceptionCodeEnum getError() {
        return error;
    }

}
