package com.lemon.servicenapi.client;

import com.lemon.vo.LoginVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
@FeignClient("service-provider")
public interface LoginClient {

    @GetMapping(value="loginIn", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String loginIn(LoginVo loginVo);
}
