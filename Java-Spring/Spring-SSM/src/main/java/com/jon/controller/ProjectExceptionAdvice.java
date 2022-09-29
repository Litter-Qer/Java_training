package com.jon.controller;

import com.jon.exception.BusinessException;
import com.jon.exception.SystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProjectExceptionAdvice {

    @ExceptionHandler(BusinessException.class)
    public Result doBusinessException(BusinessException ex) {
        System.out.println("业务异常 当场逮捕");
        return new Result(ex.getCode(), null, ex.getMessage());
    }

    @ExceptionHandler(SystemException.class)
    public Result doSystemException(SystemException ex) {
        System.out.println("系统异常 当场逮捕");
        return new Result(ex.getCode(), null, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result doException(Exception ex) {
        System.out.println("很严重的异常 直接逮捕");
        return new Result(Code.SYSTEM_UNKNOWN_ERR, null, "系统繁忙，请稍后。");
    }
}