package com.jon.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class MyAdvice {
    @Pointcut("execution(void com.jon.mapper.BookDao.update())")
    private void pt() {
    }

    @Pointcut("execution(int com.jon.mapper.BookDao.select())")
    private void pt2() {
    }

//    @Before("pt2()")
    public void before(JoinPoint jp) {
        Signature signature = jp.getSignature();
        System.out.println(Arrays.toString(jp.getArgs()));
        System.out.println("before advice ...");
    }

    //    @After("pt()")
    public void after() {
        System.out.println("after advice ...");
    }

    //    @Around("pt()")
    public Object around(ProceedingJoinPoint pjp) {
        System.out.println("around before advice ...");
        try {
            return pjp.proceed();
        } catch (Throwable e) {
            System.out.println("around proceeding failed");
            e.printStackTrace();
        }
        System.out.println("around after advice ...");
        return null;
    }

    //    @Around("pt2()")
    public Object aroundSelect(ProceedingJoinPoint pjp) {
        System.out.println("around before advice ...");
        try {
            return pjp.proceed();
        } catch (Throwable e) {
            System.out.println("around proceeding failed");
            e.printStackTrace();
        }
        System.out.println("around after advice ...");
        return 200;
    }

    //    @AfterReturning("pt2()")
    public void afterReturning() {
        System.out.println("afterReturning advice ...");
    }

    //    @AfterThrowing("pt2()")
    public void afterThrowing() {
        System.out.println("afterThrowing advice ...");
    }

}
