package com.lemon.servicenapi.client;

import com.lemon.vo.LoginResult;
import com.lemon.vo.LoginVo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * description: 登出失败熔断容器
 *
 * @author lemon
 * @date 2019-07-30 11:30:06 创建
 */
@Component
public class LoginOutClientFactory implements FallbackFactory<LoginOutClient> {
    @Override
    public LoginOutClient create(Throwable cause) {

        return new LoginOutClientWithFactory() {

            @Override
            public LoginResult loginOut(LoginVo loginVo) {
                LoginResult result = new LoginResult();
                result.setMsg("网络开了小差~~~");
                result.setLoginTime(new Date());
                return result;
            }
        };
    }
}
