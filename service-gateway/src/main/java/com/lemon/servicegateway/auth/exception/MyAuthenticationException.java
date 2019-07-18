package com.lemon.servicegateway.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * description: 授权异常类
 *
 * @author lemon
 * @date 2019-07-18 15:21:06 创建
 */
public class MyAuthenticationException extends ResponseStatusException {

    public MyAuthenticationException(HttpStatus status) {
        super(status);
    }

    public MyAuthenticationException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public MyAuthenticationException(HttpStatus status, String reason, Throwable cause) {
        super(status, reason, cause);
    }
}
