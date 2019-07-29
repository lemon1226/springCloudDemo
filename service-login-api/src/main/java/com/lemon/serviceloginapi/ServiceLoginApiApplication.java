package com.lemon.serviceloginapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class ServiceLoginApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceLoginApiApplication.class, args);
    }

}
