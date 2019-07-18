package com.lemon.servicegateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * description: 授权异常类
 *
 * @author lemon
 * @date 2019-07-18 15:21:06 创建
 */
public class AuthenticationException extends ResponseStatusException {

    public AuthenticationException(HttpStatus status) {
        super(status);
    }

    public AuthenticationException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public AuthenticationException(HttpStatus status, String reason, Throwable cause) {
        super(status, reason, cause);
    }
}
