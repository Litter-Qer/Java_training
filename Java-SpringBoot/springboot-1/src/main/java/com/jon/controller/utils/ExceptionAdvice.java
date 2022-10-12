package com.jon.controller.utils;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {
    /**
     * 拦截所有异常信息
     *
     * @param e 异常对象
     */
    @ExceptionHandler
    public R doException(Exception e) {
        e.printStackTrace();
        return new R("系统异常");
    }
}
