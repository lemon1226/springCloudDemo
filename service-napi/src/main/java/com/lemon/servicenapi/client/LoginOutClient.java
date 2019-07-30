package com.lemon.servicenapi.client;

import com.lemon.vo.LoginResult;
import com.lemon.vo.LoginVo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * description:
 *
 * @author lemon
 * @date 2019-07-30 11:29:06 创建
 */
//@Component
//@FeignClient(value = "service-provider—LoginOut", fallbackFactory = LoginOutClientFactory.class)
public interface LoginOutClient {

    @GetMapping(value="loginOut", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    LoginResult loginOut(LoginVo loginVo);
}
