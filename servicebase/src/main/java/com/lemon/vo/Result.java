package com.lemon.vo;

import lombok.Data;

/**
 * description: 通用结果类
 *
 * @author lemon
 * @date 2019-07-16 10:20:06 创建
 */
@Data
public class Result<T> {

    private Integer code;

    private T re;

    private String msg;
}
