package com.lemon.serviceloginapi.service;

import com.lemon.vo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * description: 登录service
 *
 * @author lemon
 * @date 2019-07-18 17:15:06 创建
 */
@FeignClient("service-login-service")
@Component
public interface LoginService {

    @GetMapping("login")
    public User login(@RequestParam(value = "userName") String userName);
}
