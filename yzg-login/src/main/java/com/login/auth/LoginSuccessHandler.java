package com.login.auth;


import com.common.utils.IpAddress;
import com.login.pojo.SUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * YZG on 2017/4/22.
 */
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        // 获得授权后可得到用户信息 可使用SUService进行数据库操作
        SUser userDetails = (SUser) authentication.getPrincipal();

        // 输出登录提示信息
        System.out.println("管理员" + userDetails.getEmail() + "登录");
        System.out.println("IP:" + IpAddress.getIpAddr(request));
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
