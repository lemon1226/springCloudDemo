package com.lemon.servicegateway.auth.filter;

import com.lemon.baseutils.util.TokenUtils;
import com.lemon.servicegateway.auth.config.TokenProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;

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
            ServerHttpRequest request = exchange.getRequest();
            List<HttpCookie> cookieList= request.getCookies().get(tokenProperties.getCookieName());

            if (!CollectionUtils.isEmpty(cookieList)) {
                HttpCookie cookie = cookieList.get(0);
                String authToken = cookie.getValue();

                Date expirationDate = TokenUtils.getExpirationDateFromToken(authToken, tokenProperties.getSecret());

                if (null != expirationDate && expirationDate.getTime() - new Date().getTime() <= tokenProperties.getRefreshInterval() * 1000) {
                    String username = TokenUtils.getUsernameFromToken(authToken, tokenProperties.getSecret());
                    List<String> authorities = TokenUtils.getAuthoritiesFromToken(authToken, tokenProperties.getSecret());

                    if (username != null) {
                        String tokenUUID = TokenUtils.generateToken(username, authorities,
                                tokenProperties.getExpiration(), tokenProperties.getSecret());

                        ResponseCookie responseCookie = ResponseCookie.from(tokenProperties.getCookieName(), tokenUUID)
                                .maxAge(tokenProperties.getExpiration()).build();
                        ResponseCookie secretCookie = ResponseCookie.from("secret", tokenProperties.getSecret())
                                .maxAge(tokenProperties.getExpiration()).build();

                        request.getCookies().clear();
                        exchange.getResponse().addCookie(responseCookie);
                        exchange.getResponse().addCookie(secretCookie);
                    }
                }
            }
        }catch (Exception e){}

        return chain.filter(exchange);
    }
}
