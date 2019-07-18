package com.lemon.servicegateway.controller;

import com.lemon.exception.BusinessRuntimeException;
import com.lemon.servicegateway.service.TokenService;
import com.lemon.utils.ResultUtil;
import com.lemon.vo.Result;
import com.lemon.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

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
    private TokenService tokenService;

    @PostMapping(value = "/login")
    public Mono<Result> login(User user){

        return getUserFromDatabase(user.getUsername()).map((userDetails) -> {

            try {
                checkAccount(user, userDetails);
            }catch (BusinessRuntimeException e){
                return ResultUtil.busFail(e.getMessage());
            }

            if (user.getPassword().equals(userDetails.getPassword())) {
                return ResultUtil.success(tokenService.generateToken(userDetails));
            }

            return ResultUtil.busFail();
        }).defaultIfEmpty(ResultUtil.busFail());
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
        if (!dataUser.getPassword().equals(user.getPassword())){
            throw new BusinessRuntimeException("密码错误！");
        }
    }

    @RequestMapping(value = "/auth/getAuthentication",method = RequestMethod.POST)
    public Result getAuthentication(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResultUtil.success(authentication.getAuthorities());
    }

    private Mono<User> getUserFromDatabase(String username) {
        if("admin".equals(username)){
            User user = new User();
            user.setUsername(username);
            user.setPassword("admin");
            user.setEnable(true);
            user.setAuthorities("1,2,3,4,5");
            user.setLastPasswordChange(22222L);
            return Mono.just(user);
        }
        return Mono.just(new User());
    }
}
