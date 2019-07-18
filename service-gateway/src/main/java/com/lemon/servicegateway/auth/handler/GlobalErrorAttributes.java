package com.lemon.servicegateway.auth.handler;

import com.lemon.servicegateway.auth.exception.MyAuthenticationException;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

/**
 * description: 目前没有作用，如果想自定义返回response参数，写一个自定义类继承DefaultErrorAttributes
 *
 * @author lemon
 * @date 2019-07-18 15:19:06 创建
 */
@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
        Map<String, Object> map = super.getErrorAttributes(request, includeStackTrace);

        if (getError(request) instanceof MyAuthenticationException) {
            return map;
        }
        return map;
    }
}
