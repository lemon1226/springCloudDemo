package com.lemon.servicesecurity.service.impl;

import com.lemon.servicesecurity.service.TokenDetail;

/**
 * description:
 *
 * @author
 * @date 2019-07-11 18:00:06 创建
 */
public class TokenDetailImpl implements TokenDetail {

    private final String username;

    public TokenDetailImpl(String username) {
        this.username = username;
    }

    @Override
    public String getUsername() {
        return this.username;
    }
}
