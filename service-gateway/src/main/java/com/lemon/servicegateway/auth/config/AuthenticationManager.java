package com.lemon.servicegateway.auth.config;

import com.lemon.baseutils.util.TokenUtils;
import com.lemon.servicegateway.auth.exception.MyAuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;

/**
 * description: 权限管理类
 *
 * @author lemon
 * @date 2019-07-17 11:35:06 创建
 */
@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

    @Autowired
    private TokenProperties tokenProperties;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();

        String username;
        List<String> authoritieList;
        try {
            username = TokenUtils.getUsernameFromToken(authToken, tokenProperties.getSecret());
            authoritieList = TokenUtils.getAuthoritiesFromToken(authToken, tokenProperties.getSecret());
        } catch (Exception e) {
            username = null;
            authoritieList = null;
        }

        if(username == null || CollectionUtils.isEmpty(authoritieList)){
            return Mono.error(new MyAuthenticationException(HttpStatus.UNAUTHORIZED, "用户信息失效，请重新登录"));
        }

        Collection<? extends GrantedAuthority> authorities;
        try {
            authorities = AuthorityUtils.createAuthorityList((String[]) authoritieList.toArray());
        } catch (Exception e) {
            authorities = null;
        }

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                username,
                username,
                authorities);

        return Mono.just(auth);
    }
}
