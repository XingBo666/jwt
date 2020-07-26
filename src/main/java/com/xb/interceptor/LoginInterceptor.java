package com.xb.interceptor;

import com.xb.util.JwtUtil;
import com.xb.util.RequestStorage;
import com.xb.util.UserTokenModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    RequestStorage requestStorage;

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String pathInfo = request.getPathInfo();

        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            return true;
        }

        String token = request.getHeader("token");
        if (token == null){
            return false;
        }
        //  解析token
        UserTokenModel model = JwtUtil.verify(token);
        if (null == model){
            return false;
        }else {
            requestStorage.setId(model.getId());
            Object o = redisTemplate.opsForValue().get("login:" + model.getId());
            if (o == null || (String) o != token){
                return false;
            }
        }
        //  从redis中去取token，并进行比对

        return true;
    }
}
