package com.lemon.serviceloginservice.service;

import com.lemon.vo.User;

/**
 * description: 登录service
 *
 * @author lemon
 * @date 2019-07-18 17:15:06 创建
 */
public interface LoginService {

    User getUserFromDatabase(String username);
}
