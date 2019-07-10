package com.lemon.servicenapi.controller;

import com.lemon.servicenapi.client.LoginClient;
import com.lemon.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description:
 *
 * @author
 * @date 2019-07-05 13:35:06 创建
 */
@RestController
public class LoginController {

    @Autowired
    private LoginClient loginClient;

    @GetMapping("loginIn")
    public String loginIn(LoginVo loginVo){
        return loginClient.loginIn(loginVo);
    }
}
