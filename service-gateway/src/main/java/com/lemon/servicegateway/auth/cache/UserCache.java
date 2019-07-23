package com.lemon.servicegateway.auth.cache;

import com.lemon.baseutils.cache.ExpiryCache;
import com.lemon.servicegateway.auth.vo.UserDetailVo;

/**
 * description: 用户信息缓存
 *
 * @author lemon
 * @date 2019-07-23 16:13:06 创建
 */
public class UserCache {

    private static ExpiryCache<String, UserDetailVo> userCache = new ExpiryCache<>();


    public static UserDetailVo getUser(String username){
        return userCache.get(username);
    }

    public static UserDetailVo setUser(String username, UserDetailVo user){
        return userCache.put(username, user);
    }

    public static UserDetailVo deleteUser(String username){
        return userCache.remove(username);
    }
}
