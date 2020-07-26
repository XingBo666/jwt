package com.xb.controller;

import com.xb.service.UserService;
import com.xb.util.BaseResult;
import com.xb.vo.UserLoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("login")
    public BaseResult login(@RequestBody UserLoginVo vo){
        return userService.login(vo);
    }

    @RequestMapping("findAllUser")
    public BaseResult findAllUser(){
        return userService.findAllUser();
    }

    @RequestMapping("registry")
    public String registry() {
        return "hello";
    }
}
