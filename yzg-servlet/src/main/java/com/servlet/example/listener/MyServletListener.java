package com.servlet.example.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * 监听servletContext的创建与销毁
 * 使用@WebListener注解,实现serlvetContextListener接口
 * @author YZG
 */
@WebListener
public class MyServletListener implements ServletContextListener{
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("servletContext初始化!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("servletContext销毁!");
    }
}
