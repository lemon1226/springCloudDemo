package com.lemon.servicenapi.controller;

import com.lemon.servicenapi.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description:
 *
 * @author
 * @date 2019-07-05 13:35:06 创建
 */
@RestController
public class MainController {

    @Autowired
    private MainService mainService;

    @GetMapping("hello")
    public String hello(String name){
        return mainService.hello(name);
    }
}