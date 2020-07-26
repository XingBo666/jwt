package com.xb.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class RequestAspect {

    @Pointcut("execution(* com.xb.controller..*.*(..))")
    public void requestPoint(){}

    @Around("requestPoint()")
    public void aroundRequest(ProceedingJoinPoint joinPoint){
        Object args[] = joinPoint.getArgs();

    }
}
