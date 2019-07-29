package com.lemon.serviceloginapi.controller;

import com.lemon.baseutils.util.TokenUtils;
import com.lemon.exception.BusinessRuntimeException;
import com.lemon.serviceloginapi.config.TokenProperties;
import com.lemon.serviceloginapi.service.LoginService;
import com.lemon.utils.CookieUtil;
import com.lemon.utils.ResultUtil;
import com.lemon.vo.Result;
import com.lemon.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * description: 登录控制器
 *
 * @author lemon
 * @date 2019-07-29 10:10:06 创建
 */
@RestController
@RequestMapping("user")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private TokenProperties tokenProperties;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @PostMapping(value = "/login")
    public Result login(User user, HttpServletResponse response){

        User userData = loginService.login(user.getUsername());

        checkAccount(user, userData);


        if (bCryptPasswordEncoder.matches(user.getPassword(), userData.getPassword())) {

            List<String> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(
                    userData.getAuthorities()).stream().map(GrantedAuthority::getAuthority).
                    collect(Collectors.toList());

            String token = TokenUtils.generateToken(userData.getUsername(), authorities
                    ,tokenProperties.getExpiration(), tokenProperties.getSecret());
            if(null != token) {
                CookieUtil.addCookie(response, tokenProperties.getCookieName(), token,
                        tokenProperties.getExpiration().intValue());
                return ResultUtil.success();
            }
        }

        return ResultUtil.busFail("密码错误");
    }

    @PostMapping(value = "getAuthentications")
    public Result<List<String>> getAuthentication(HttpServletRequest request){
        String cookieValue = CookieUtil.getUid(request, tokenProperties.getCookieName());

        return ResultUtil.success(TokenUtils.getAuthoritiesFromToken(cookieValue,
                tokenProperties.getSecret()));
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
