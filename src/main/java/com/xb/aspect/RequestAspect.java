package com.xb.aspect;

import com.alibaba.fastjson.JSON;
import com.xb.util.BaseResult;
import com.xb.util.JwtUtil;
import com.xb.util.UserTokenModel;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;

@Aspect
@Log4j2
@Component
public class RequestAspect {

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @Pointcut("execution(* com.xb.controller.*.*(..))")
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint joinpoint) throws Throwable {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null){
            return BaseResult.failureUnknown();
        }

        HttpServletRequest request = attributes.getRequest();
        //判断是否是登陆
        System.out.println(request.getRequestURI());

        HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();

        if (!request.getRequestURI().equals("/user/login")){
            //  如果不是登陆的操作，那么验证他的token

            String token = request.getHeader("token");
            UserTokenModel verify = JwtUtil.verify(token);
            if (verify == null){
                //  校验失败
                log.error("token解析失败");
                return BaseResult.failureAuth();
            }
            //  token解析成功，去redis库中去找它
            try{
                Object o = redisTemplate.opsForValue().get("login:" + verify.getId());
                if (o == null || !((String) o).equals(token)){
                    return BaseResult.failureAuth();
                }
            }catch (Exception e){
                log.error(e);
                return BaseResult.failureUnknown();
            }
        }

        return joinpoint.proceed();
    }

    private void shutdownResponse(HttpServletResponse response) throws IOException {
        response.getOutputStream().close();
    }
}
