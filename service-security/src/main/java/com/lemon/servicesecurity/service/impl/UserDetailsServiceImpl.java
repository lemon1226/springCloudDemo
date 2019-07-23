package com.lemon.servicesecurity.service.impl;

import com.lemon.servicesecurity.factory.SecurityModelFactory;
import com.lemon.servicesecurity.vo.User;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * description:
 *
 * @author lemon
 * @date 2019-07-12 13:39:06 创建
 */
@Service
@Primary
public class UserDetailsServiceImpl implements UserDetailsService {



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserFromDatabase(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return SecurityModelFactory.create(user);
        }
    }

    private User getUserFromDatabase(String username) {
        if("lemon".equals(username)){
            User user = new User();
            user.setUsername(username);
            user.setPassword("lemon");
            user.setEnable(true);
            user.setAuthorities("1,2,3,4,5");
            user.setLastPasswordChange(1111L);
            return user;
        }
        if("admin".equals(username)){
            User user = new User();
            user.setUsername(username);
            user.setPassword("admin");
            user.setEnable(true);
            user.setAuthorities("1,2,3,4,5");
            user.setLastPasswordChange(1111L);
            return user;
        }
        return null;
    }
}
