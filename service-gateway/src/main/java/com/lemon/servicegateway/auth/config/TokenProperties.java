package com.lemon.servicegateway.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * description:
 *
 * @author lemon
 * @date 2019-07-12 14:26:06 创建
 */
@Data
@Component
@ConfigurationProperties(prefix = "token")
public class TokenProperties {

    private String secret;

    private Long expiration;

    private String header;
}
