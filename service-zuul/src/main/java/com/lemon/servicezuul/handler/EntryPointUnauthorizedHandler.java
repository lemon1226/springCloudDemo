package com.lemon.servicezuul.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * description: 定义 401（未登录） 处理器
 *
 * @author lemon
 * @date 2019-07-12 16:20:06 创建
 */
@Component
public class EntryPointUnauthorizedHandler implements AuthenticationEntryPoint {

    /**
     * 未登录或无权限时触发的操作
     * @param httpServletRequest
     * @param httpServletResponse
     * @param e
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        //返回json形式的错误信息
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");

        httpServletResponse.getWriter().println("{\"code\":401,\"message\":\"没有登录信息，访问无效！\",\"data\":\"\"}");
        httpServletResponse.getWriter().flush();
    }
}
