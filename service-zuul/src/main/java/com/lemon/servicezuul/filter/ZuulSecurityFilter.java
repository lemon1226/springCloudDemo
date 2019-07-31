package com.lemon.servicezuul.filter;

import com.lemon.baseutils.util.TokenUtils;
import com.lemon.servicezuul.config.TokenProperties;
import com.lemon.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * description:
 *
 * @author 张萌（meng.zhang04@ucarinc.com）
 * @date 2019-07-31 09:28:06 创建
 */
public class ZuulSecurityFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private TokenProperties tokenProperties;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        if(CookieUtil.isExist((HttpServletRequest)request, tokenProperties.getCookieName())) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String cookieValue = CookieUtil.getUid(httpRequest, tokenProperties.getCookieName());

            String username;
            try {
                username = TokenUtils.getUsernameFromToken(cookieValue, tokenProperties.getSecret());
            } catch (Exception e) {
                username = null;
            }

            if (username == null) {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().print("{\"code\":\"401\",\"data\":\"\",\"message\":\"用户信息失效，请重新登录\"}");
                return;
            }

            // 生成通过认证
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, null);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
            // 将权限写入本次会话
            SecurityContextHolder.getContext().setAuthentication(authentication);

            //当cookie快失效，但是却有请求时，更新cookie有效期
            Date expirationDate = TokenUtils.getExpirationDateFromToken(cookieValue, tokenProperties.getSecret());
            if (null != expirationDate && expirationDate.getTime() - new Date().getTime() <= tokenProperties.getRefreshInterval() * 1000) {

                List<String> authorities = TokenUtils.getAuthoritiesFromToken(cookieValue, tokenProperties.getSecret());
                String tokenUUID = TokenUtils.generateToken(username, authorities,
                        tokenProperties.getExpiration(), tokenProperties.getSecret());

                HttpServletResponse httpResponse = (HttpServletResponse)response;

                CookieUtil.removeCookie(httpResponse, tokenProperties.getCookieName());
                CookieUtil.removeCookie(httpResponse, "secret");

                CookieUtil.addCookie(httpResponse, tokenProperties.getCookieName(), tokenUUID,
                        tokenProperties.getExpiration().intValue());
                CookieUtil.addCookie(httpResponse, "secret", tokenProperties.getSecret(),
                        tokenProperties.getExpiration().intValue());
            }
        }

        chain.doFilter(request, response);
    }
}
