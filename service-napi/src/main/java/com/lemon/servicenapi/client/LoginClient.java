package com.lemon.servicenapi.client;

import com.lemon.vo.LoginResult;
import com.lemon.vo.LoginVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
@FeignClient(value = "service-provider", fallback = LoginClientFallback.class)
public interface LoginClient {

    @GetMapping(value="loginIn", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public LoginResult loginIn(LoginVo loginVo);
}
