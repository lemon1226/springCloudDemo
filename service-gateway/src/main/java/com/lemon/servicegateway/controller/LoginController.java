package com.lemon.servicegateway.controller;

import com.lemon.baseutils.util.BCryptPasswordEncoderUtils;
import com.lemon.baseutils.util.TokenUtils;
import com.lemon.exception.BusinessRuntimeException;
import com.lemon.servicegateway.auth.config.TokenProperties;
import com.lemon.servicegateway.auth.exception.MyAuthenticationException;
import com.lemon.servicegateway.auth.vo.UserDetailVo;
import com.lemon.servicegateway.service.LoginService;
import com.lemon.servicegateway.util.RedisUtil;
import com.lemon.utils.ResultUtil;
import com.lemon.vo.Result;
import com.lemon.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

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
    private TokenProperties tokenProperties;

    @Autowired
    private LoginService loginService;

    @Autowired
    private RedisUtil redisUtil;

    @PostMapping(value = "/login")
    public Result login(User user){

        User userDetails = loginService.getUserFromDatabase(user.getUsername());

        checkAccount(user, userDetails);

        if (BCryptPasswordEncoderUtils.match(user.getPassword(), userDetails.getPassword())) {
            return ResultUtil.success(TokenUtils.generateToken(userDetails.getUsername(),
                    userDetails.getPassword(), tokenProperties.getExpiration(), tokenProperties.getSecret()));
        }

        return ResultUtil.busFail("密码错误");
    }

    @PostMapping(value = "getAuthentications")
    public Result<Collection<? extends GrantedAuthority>> getAuthentication(ServerWebExchange swe){
        ServerHttpRequest request = swe.getRequest();
        String authToken = request.getHeaders().getFirst(tokenProperties.getHeader());
        UserDetailVo user = (UserDetailVo) redisUtil.get(authToken);
        if(null == user){
            throw new MyAuthenticationException(HttpStatus.FORBIDDEN, "用户信息过期，请重新登录");
        }
        return ResultUtil.success(user.getAuthorities());
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
