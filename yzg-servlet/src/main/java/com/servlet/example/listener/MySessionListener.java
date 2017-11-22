package com.servlet.example.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 监听session的创建与销毁
 * 如果使用代码的方式注册Listener,需要使用ServletListenerRegistrationBean,与servlet类似;
 * @author YZG
 */
@WebListener
public class MySessionListener implements HttpSessionListener{

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("Session被创建!");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("Session销毁!");
    }
}
