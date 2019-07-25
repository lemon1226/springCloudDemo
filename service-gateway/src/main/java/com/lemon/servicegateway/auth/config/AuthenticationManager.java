package com.lemon.servicegateway.auth.config;

import com.lemon.baseutils.util.TokenUtils;
import com.lemon.servicegateway.auth.exception.MyAuthenticationException;
import com.lemon.servicegateway.auth.vo.UserDetailVo;
import com.lemon.servicegateway.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

    @Autowired
    private RedisUtil redisUtil;

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
            return Mono.error(new MyAuthenticationException(HttpStatus.UNAUTHORIZED, "用户信息失效，请重新登录"));
        }

        UserDetailVo userInfo = (UserDetailVo) redisUtil.get(authToken);
        boolean isReCheck = false;
        if (userInfo == null) {
            isReCheck = true;
        } else {
            String password;
            try {
                password = TokenUtils.getPasswordFromToken(authToken, tokenProperties.getSecret());
            } catch (Exception e) {
                password = null;
            }
            if(password == null){
                return Mono.error(new MyAuthenticationException(HttpStatus.UNAUTHORIZED, "用户信息不正确，请登录"));
            }
            if(!userInfo.getPassword().equals(password)){
                isReCheck = true;
            }
        }

        if(isReCheck){
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UserDetailVo user = (UserDetailVo) userDetails;
            if (TokenUtils.validateToken(authToken, user.getUsername(), user.getPassword(),
                    tokenProperties.getSecret(), user.getLastPasswordReset())) {

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        username,
                        user.getAuthorities());

                redisUtil.set(authToken, user);
                redisUtil.expire(authToken, tokenProperties.getExpiration());
                return Mono.just(auth);
            } else {
                 return Mono.error(new MyAuthenticationException(HttpStatus.UNAUTHORIZED, "用户信息失效，请重新登录"));
            }
        }
        return Mono.just(new UsernamePasswordAuthenticationToken(
                userInfo,
                userInfo.getUsername(),
                userInfo.getAuthorities()));
    }
}
