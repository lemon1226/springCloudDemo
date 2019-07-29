package com.lemon.serviceloginservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ServiceLoginServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceLoginServiceApplication.class, args);
    }

}
