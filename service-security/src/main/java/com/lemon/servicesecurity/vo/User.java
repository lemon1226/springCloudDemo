package com.lemon.servicesecurity.vo;

import com.lemon.servicesecurity.service.TokenDetail;
import lombok.Data;

/**
 * description:
 *
 * @author
 * @date 2019-07-12 13:57:06 创建
 */
@Data
public class User implements TokenDetail {

    private String username;
    private String password;
    private String authorities;
    private Long lastPasswordChange;
    private Boolean enable;
}
