package com.lemon.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * description:
 *
 * @author lemon
 * @date 2019-07-10 11:42:06 创建
 */
@Data
public class LoginResult {

    private String msg;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date loginTime;
}
