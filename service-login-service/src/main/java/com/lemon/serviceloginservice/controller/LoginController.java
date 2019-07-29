package com.lemon.serviceloginservice.controller;

import com.lemon.serviceloginservice.service.LoginService;
import com.lemon.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description: 登录控制器
 *
 * @author lemon
 * @date 2019-07-29 10:10:06 创建
 */
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("login")
    public User login(String userName){
        return loginService.getUserFromDatabase(userName);
    }
}
