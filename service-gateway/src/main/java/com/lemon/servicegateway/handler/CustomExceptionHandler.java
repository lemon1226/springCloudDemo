package com.lemon.servicegateway.handler;

import com.lemon.exception.BusinessRuntimeException;
import com.lemon.utils.ResultUtil;
import com.lemon.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * description: springmvc全局异常处理
 *
 * @author lemon
 * @date 2019-07-16 17:22:06 创建
 */
@RestController
@ControllerAdvice
public class CustomExceptionHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(BusinessRuntimeException.class)
    public Result<Object> handleBusinessRuntimeException(BusinessRuntimeException e) {
        LOGGER.error(e.getMessage(), e);
        return ResultUtil.busFail(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result<Object> handleException(Exception e) {
        LOGGER.error("系统异常", e);
        return ResultUtil.fail("系统异常");
    }
}
