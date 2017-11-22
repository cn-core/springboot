package com.login.auth;


import com.login.pojo.SUser;
import com.login.service.impl.SUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


/**
 * 自定义用户详细信息
 * @author YZG on 2017/4/22.
 */
@Component
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SUserServiceImpl suserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // SUser对应数据库中的用户表,是最终存储用户和密码的表,可自定义
        // 使用SUser中的email作为用户名;
        SUser user = suserService.findUserByEmail(username);
        if (user == null){
            throw new UsernameNotFoundException("UserName" + username + "not found");
        }
        // securityUser实现UserDetails并将SUser的Email映射为username
        return new SecurityUser(user);
    }

}

