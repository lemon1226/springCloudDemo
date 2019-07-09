package com.lemon.servicenapi.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient("service-provider")
public interface MainClient {

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String hello(@RequestParam(value = "name") String name);
}
