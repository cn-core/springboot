package com.servlet.example.servlet;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author YZG
 */
@Configuration
public class ConfigServlet {

    @Bean
    public ServletRegistrationBean servletRegistration(){
        return new ServletRegistrationBean(new MyServlet(),"/jsp/*");
    }
}
