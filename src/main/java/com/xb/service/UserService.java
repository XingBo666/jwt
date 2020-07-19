package com.xb.service;

import com.xb.entity.User;
import com.xb.repository.UserRepository;
import com.xb.util.BaseResult;
import com.xb.util.JwtUtil;
import com.xb.vo.UserLoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    public BaseResult login(UserLoginVo vo) {
        User user = userRepository.findByNickNameAndPassword(vo.getUsername(),vo.getPassword());
        if (null == user){
            return BaseResult.failure("用户名或密码错误");
        }
        return BaseResult.success(JwtUtil.sign(user.getId()));
    }
}
