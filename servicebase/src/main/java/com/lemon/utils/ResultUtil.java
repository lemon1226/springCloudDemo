package com.lemon.utils;

import com.lemon.vo.Result;

/**
 * description:
 *
 * @author lemon
 * @date 2019-07-16 10:27:06 创建
 */
public class ResultUtil{

    public static Result success(){
        return success(null);
    }

    public static Result success(Object re){
        return success(re, "成功");
    }

    public static Result success(Object re, String msg){
        Result result = new Result();
        result.setCode(1);
        result.setMsg(msg);
        result.setRe(re);
        return result;
    }

    public static Result fail(){
        return fail(null);
    }

    public static Result fail(Object re){
        return fail(re, "失败");
    }

    public static Result fail(String msg){
        return fail(null, "msg");
    }

    public static Result fail(Object re, String msg){
        Result result = new Result();
        result.setCode(0);
        result.setMsg(msg);
        result.setRe(re);
        return result;
    }
}
