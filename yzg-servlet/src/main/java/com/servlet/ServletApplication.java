package com.servlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author YZG
 */
@SpringBootApplication
@ServletComponentScan
public class ServletApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServletApplication.class,args);
    }

    /**
     * 修改DispatcherServlet默认配置
     *
     */
    /*@Bean
    public ServletRegistrationBean dispatcherRegistration() {
        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet);
        registration.getUrlMappings().clear();
        registration.addUrlMappings("*.do");
        registration.addUrlMappings("*.json");
        return registration;
    }*/
}
