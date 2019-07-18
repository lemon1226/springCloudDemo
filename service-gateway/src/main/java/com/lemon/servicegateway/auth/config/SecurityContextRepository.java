package com.lemon.servicegateway.auth.config;

import com.lemon.servicegateway.auth.exception.MyAuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * description:
 *
 * @author lemon
 * @date 2019-07-17 11:43:06 创建
 */
@Component
public class SecurityContextRepository implements ServerSecurityContextRepository {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProperties tokenProperties;

    @Override
    public Mono<Void> save(ServerWebExchange swe, SecurityContext sc) {
        throw new MyAuthenticationException(HttpStatus.FORBIDDEN);
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange swe) {
        ServerHttpRequest request = swe.getRequest();
        String authHeader = request.getHeaders().getFirst(tokenProperties.getHeader());

        if (authHeader != null) {
            Authentication auth = new UsernamePasswordAuthenticationToken(authHeader, authHeader);
            return this.authenticationManager.authenticate(auth).map((authentication) -> {
                return new SecurityContextImpl(authentication);
            });
        } else {
            return Mono.empty();
        }
    }
}
