package com.lemon.serviceprovider6002.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description:
 *
 * @author lemon
 * @date 2019-07-05 10:14:06 创建
 */
@RestController
public class HelloWorldController {

    @GetMapping("hello")
    public String hello(String name){
        return "你好，" + name + " 来自6002";
    }
}