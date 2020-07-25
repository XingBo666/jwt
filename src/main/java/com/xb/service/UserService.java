package com.xb.service;

import com.xb.entity.User;
import com.xb.repository.UserRepository;
import com.xb.util.BaseResult;
import com.xb.util.JwtUtil;
import com.xb.vo.UserLoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    public BaseResult login(UserLoginVo vo) {
        User user = userRepository.findByNickNameAndPassword(vo.getUsername(),vo.getPassword());
        if (null == user){
            return BaseResult.failure("用户名或密码错误");
        }

        String token = JwtUtil.sign(user.getId());
        //  登陆成功，将token放入redis
        redisTemplate.opsForValue().set("login:" + user.getId(),token);

        return BaseResult.success(token);
    }
}
