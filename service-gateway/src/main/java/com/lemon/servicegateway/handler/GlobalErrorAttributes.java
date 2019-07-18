package com.lemon.servicegateway.handler;

import com.lemon.servicegateway.exception.AuthenticationException;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

/**
 * description:
 *
 * @author lemon
 * @date 2019-07-18 15:19:06 创建
 */
@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
        Map<String, Object> map = super.getErrorAttributes(request, includeStackTrace);

        if (getError(request) instanceof AuthenticationException) {
            return map;
        }

        map.put("exception", "SystemException");
        map.put("message", "System Error , Check logs!");
        map.put("status", "500");
        map.put("error", " System Error ");
        return map;
    }
}
