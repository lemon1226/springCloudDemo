package com.lemon.servicesecurity.controller;

import com.lemon.servicesecurity.utils.TokenUtils;
import com.lemon.servicesecurity.vo.ResultMap;
import com.lemon.servicesecurity.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private TokenUtils tokenUtils;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResultMap login(User user){
        User dataUser = getUserFromDatabase(user.getUsername());
        ResultMap ifLoginFail = checkAccount(user, dataUser);
        if (ifLoginFail != null){
            return ifLoginFail;
        }

        return new ResultMap().success().data(tokenUtils.generateToken(dataUser));
    }

    private ResultMap checkAccount(User user, User dataUser){
        if (dataUser == null){
            return new ResultMap().fail("434").message("账号不存在！").data("");
        }

        if (dataUser.getEnable() == false){
            return new ResultMap().fail("452").message("账号在黑名单中").data("");
        }
        if (!bCryptPasswordEncoder.matches(user.getPassword(), dataUser.getPassword())){
            return new ResultMap().fail("438").message("密码错误！").data("");
        }
        return null;
    }

    @RequestMapping(value = "/admin/getSome",method = RequestMethod.POST)
    public ResultMap getSome(){
        return new ResultMap().success().data("访问成功了");
    }

    @RequestMapping(value = "/auth/getAuthentication",method = RequestMethod.POST)
    public ResultMap getAuthentication(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new ResultMap().success().data(authentication.getAuthorities());
    }

    private User getUserFromDatabase(String username) {
        if("lemon".equals(username)){
            User user = new User();
            user.setUsername(username);
            user.setPassword("lemon");
            user.setEnable(true);
            user.setAuthorities("1,2,3,4,5");
            user.setLastPasswordChange(22222L);
            return user;
        }
        if("admin".equals(username)){
            User user = new User();
            user.setUsername(username);
            user.setPassword("admin");
            user.setEnable(true);
            user.setAuthorities("1,2,3,4,5");
            user.setLastPasswordChange(22222L);
            return user;
        }
        return null;
    }

}
