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
        result.setCode(0);
        result.setMsg(msg);
        result.setRe(re);
        return result;
    }

    public static Result busFail(){
        return busFail(-1);
    }

    public static Result busFail(Object re){
        return busFail(-1, re, "失败");
    }

    public static Result busFail(Integer code){
        return busFail(code, null, "失败");
    }

    public static Result busFail(Integer code, String msg){
        return busFail(code, null, msg);
    }

    public static Result busFail(String msg){
        return busFail(-1, null, msg);
    }

    public static Result busFail(Integer code, Object re, String msg){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setRe(re);
        return result;
    }

    public static Result fail(String msg){
        Result result = new Result();
        result.setCode(-2);
        result.setMsg(msg);
        return result;
    }
}
