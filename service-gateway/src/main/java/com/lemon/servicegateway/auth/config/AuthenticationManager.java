package com.lemon.servicegateway.auth.config;

import com.lemon.baseutils.util.TokenUtils;
import com.lemon.servicegateway.auth.exception.MyAuthenticationException;
import com.lemon.servicegateway.auth.vo.UserDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * description: 权限管理类
 *
 * @author lemon
 * @date 2019-07-17 11:35:06 创建
 */
@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenProperties tokenProperties;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();

        String username;
        try {
            username = TokenUtils.getUsernameFromToken(authToken, tokenProperties.getSecret());
        } catch (Exception e) {
            username = null;
        }

        if(username == null){
            return Mono.error(new MyAuthenticationException(HttpStatus.UNAUTHORIZED, "用户名失效，请登录"));
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UserDetailVo user = (UserDetailVo) userDetails;
            if (TokenUtils.validateToken(authToken, user.getUsername(),
                    tokenProperties.getSecret(), user.getLastPasswordReset())) {

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(auth);
                return Mono.just(auth);
            }
        }

        return Mono.just(SecurityContextHolder.getContext().getAuthentication());
    }
}
