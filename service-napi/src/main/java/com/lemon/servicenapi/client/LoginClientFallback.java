package com.lemon.servicenapi.client;

import com.alibaba.fastjson.JSON;
import com.lemon.vo.LoginResult;
import com.lemon.vo.LoginVo;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * description: 断容器
 *
 * @author lemon
 * @date 2019-07-30 09:51:06 创建
 */
@Component
public class LoginClientFallback implements LoginClient{
    @Override
    public LoginResult loginIn(LoginVo loginVo) {
        LoginResult result = new LoginResult();
        result.setMsg("网络开了小差~~~");
        result.setLoginTime(new Date());
        return result;
    }
}
