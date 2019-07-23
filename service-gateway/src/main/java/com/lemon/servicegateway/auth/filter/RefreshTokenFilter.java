package com.lemon.servicegateway.auth.filter;

import com.lemon.baseutils.util.TokenUtils;
import com.lemon.servicegateway.auth.config.TokenProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Date;

/**
 * description: 如果N分钟后超时，但是有请求，延长token失效时间
 *
 * @author lemon
 * @date 2019-07-19 17:41:06 创建
 */
@Component
public class RefreshTokenFilter implements WebFilter {

    @Autowired
    private TokenProperties tokenProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        try {
            String authToken = exchange.getRequest().getHeaders().getFirst(tokenProperties.getHeader());

            if (null != authToken) {
                Date expirationDate = TokenUtils.getExpirationDateFromToken(authToken, tokenProperties.getSecret());

                if (null != expirationDate && expirationDate.getTime() - new Date().getTime() <= tokenProperties.getRefreshInterval() * 1000) {
                    String username = TokenUtils.getUsernameFromToken(authToken, tokenProperties.getSecret());
                    String password = TokenUtils.getPasswordFromToken(authToken, tokenProperties.getSecret());

                    if (username != null) {
                        String tokenUUID = TokenUtils.generateToken(username, password,
                                tokenProperties.getExpiration(), tokenProperties.getSecret());
                        exchange.getResponse().getHeaders().put(tokenProperties.getHeader(), Arrays.asList(tokenUUID));
                    }
                }
            }
        }catch (Exception e){}

        return chain.filter(exchange);
    }
}
