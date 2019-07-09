package com.lemon.servicenapi.config;

import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * description: 负载均衡配置类
 *
 * @author lemon
 * @date 2019-07-09 10:24:06 创建
 */
@Configuration
public class LoadBalancerConfig {

    @Bean
    public IRule loadBalanceRule() {
        return new MyLoadBalanceRule();
    }
}
