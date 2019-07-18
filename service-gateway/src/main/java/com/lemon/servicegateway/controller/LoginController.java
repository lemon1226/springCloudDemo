package com.lemon.servicegateway.controller;

import com.lemon.exception.BusinessRuntimeException;
import com.lemon.servicegateway.auth.service.TokenToolService;
import com.lemon.servicegateway.service.LoginService;
import com.lemon.utils.ResultUtil;
import com.lemon.vo.Result;
import com.lemon.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * description: 登录控制器
 *
 * @author lemon
 * @date 2019-07-16 16:36:06 创建
 */
@RestController
@RequestMapping("user")
public class LoginController {

    @Autowired
    private TokenToolService tokenToolService;

    @Autowired
    private LoginService loginService;

    @PostMapping(value = "/login")
    public Result login(User user){

        User userDetails = loginService.getUserFromDatabase(user.getUsername());

        checkAccount(user, userDetails);

        if (user.getPassword().equals(userDetails.getPassword())) {
            return ResultUtil.success(tokenToolService.generateToken(userDetails));
        }

        return ResultUtil.busFail("密码错误");
    }

    @PostMapping(value = "getAuthentications")
    public Result<Collection<? extends GrantedAuthority>> getAuthentication(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResultUtil.success(authentication.getAuthorities());
    }

    private void checkAccount(User user, User dataUser){
        if (dataUser == null || dataUser.getEnable() == null ||
                StringUtils.isEmpty(dataUser.getUsername()) ||
                StringUtils.isEmpty(dataUser.getPassword())){
            throw new BusinessRuntimeException("账号不存在！");
        }

        if (dataUser.getEnable() == false){
            throw new BusinessRuntimeException("账号在黑名单中");
        }
    }
}
