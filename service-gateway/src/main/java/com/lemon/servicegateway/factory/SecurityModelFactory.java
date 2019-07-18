package com.lemon.servicegateway.factory;

import com.lemon.servicegateway.service.impl.UserDetailImpl;
import com.lemon.vo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import java.util.Collection;
import java.util.Date;

/**
 * description:
 *
 * @author
 * @date 2019-07-12 14:06:06 创建
 */
public class SecurityModelFactory {

    public static UserDetailImpl create(User user) {
        Collection<? extends GrantedAuthority> authorities;
        try {
            authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getAuthorities());
        } catch (Exception e) {
            authorities = null;
        }

        Date lastPasswordReset = new Date();
        lastPasswordReset.setTime(user.getLastPasswordChange());
        return new UserDetailImpl(
                user.getUsername(),
                user.getUsername(),
                user.getPassword(),
                lastPasswordReset,
                authorities,
                true
        );
    }
}
