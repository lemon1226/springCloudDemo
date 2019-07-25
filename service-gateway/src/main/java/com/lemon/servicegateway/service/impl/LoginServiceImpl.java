package com.lemon.servicegateway.service.impl;

import com.lemon.servicegateway.service.LoginService;
import com.lemon.vo.User;
import org.springframework.stereotype.Service;

/**
 * description: 登录service
 *
 * @author lemon
 * @date 2019-07-18 17:15:06 创建
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Override
    public User getUserFromDatabase(String username) {
        User user = new User();
        if("admin".equals(username)){
            user.setUsername(username);
            user.setPassword("$2a$10$.Ame5KMmFj1UFm5MjsIH1eB0QRc1UmYyF7YqDduj9nqmyY/S6WJni");
            user.setEnable(true);
            user.setAuthorities("1,2,3,4,5");
            user.setLastPasswordChange(22222L);
            return user;
        }
        if("lemon".equals(username)){
            user.setUsername(username);
            user.setPassword("$2a$10$.Ame5KMmFj1UFm5MjsIH1eB0QRc1UmYyF7YqDduj9nqmyY/S6WJni");
            user.setEnable(true);
            user.setAuthorities("1,2,3,4,5");
            user.setLastPasswordChange(22222L);
            return user;
        }
        return user;
    }
}
