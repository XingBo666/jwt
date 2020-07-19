package com.xb.interceptor;

import com.xb.util.JwtUtil;
import com.xb.util.RequestStorage;
import com.xb.util.UserTokenModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    RequestStorage requestStorage;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String pathInfo = request.getPathInfo();

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
        }


        return true;
    }
}
