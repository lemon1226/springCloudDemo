package com.lemon.serviceprovider6001.controller;

import com.alibaba.fastjson.JSON;
import com.lemon.baseutils.util.TokenUtils;
import com.lemon.utils.CookieUtil;
import com.lemon.vo.LoginResult;
import com.lemon.vo.LoginVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
    public LoginResult loginIn(@RequestBody LoginVo loginVo, HttpServletRequest request){
        String cookieValue = CookieUtil.getUid(request, "lemon_cookie");
        String username = TokenUtils.getUsernameFromToken(cookieValue, "secret");
        LoginResult result = new LoginResult();
        result.setMsg(username + ",登录成功，来自6001，内容：" + JSON.toJSONString(loginVo));
        result.setLoginTime(new Date());
        return result;
    }
}
