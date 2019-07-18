package com.lemon.servicegateway.auth.service.impl;

import com.lemon.servicegateway.auth.exception.MyAuthenticationException;
import com.lemon.servicegateway.auth.factory.SecurityModelFactory;
import com.lemon.servicegateway.service.LoginService;
import com.lemon.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * description:
 *
 * @author lemon
 * @date 2019-07-12 13:39:06 创建
 */
@Service
@Primary
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private LoginService loginService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = loginService.getUserFromDatabase(username);

        if (user == null) {
            throw new MyAuthenticationException(HttpStatus.FORBIDDEN, "未找到对应的用户");
        } else {
            return SecurityModelFactory.create(user);
        }
    }
}
