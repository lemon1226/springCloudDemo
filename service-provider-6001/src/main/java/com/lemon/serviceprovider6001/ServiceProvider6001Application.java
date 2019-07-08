package com.lemon.serviceprovider6001;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ServiceProvider6001Application {

    public static void main(String[] args) {
        SpringApplication.run(ServiceProvider6001Application.class, args);
    }

}
