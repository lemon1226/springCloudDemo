package com.lemon.vo;

import lombok.Data;

import java.util.Date;

/**
 * description:
 *
 * @author lemon
 * @date 2019-07-10 11:38:06 创建
 */
@Data
public class LoginVo {

    private String userName;

    private String password;

    private Date loginTime;
}
