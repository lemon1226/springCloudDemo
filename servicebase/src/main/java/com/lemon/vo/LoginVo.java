package com.lemon.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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

    @DateTimeFormat(pattern = "yyyy-MM-DD HH:mm:ss")
    private Date loginTime;
}
