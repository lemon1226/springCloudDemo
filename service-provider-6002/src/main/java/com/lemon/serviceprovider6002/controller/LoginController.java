package com.lemon.serviceprovider6002.controller;

import com.alibaba.fastjson.JSON;
import com.lemon.vo.LoginResult;
import com.lemon.vo.LoginVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * description:
 *
 * @author lemon
 * @date 2019-07-05 10:14:06 创建
 */
@RestController
public class LoginController {

    @GetMapping("loginIn")
    public LoginResult loginIn(@RequestBody LoginVo loginVo){
        LoginResult result = new LoginResult();
        result.setMsg("登录成功，来自6002，内容：" + JSON.toJSONString(loginVo));
        result.setLoginTime(new Date());
        return result;
    }
}
