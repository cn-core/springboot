package com.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author yangzhiguo on 2017/8/29.
 */
@SpringBootApplication
public class WebApplication {// extends SpringBootServletInitializer

    // @Override
    // protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    //     return builder.sources(WebApplication.class);
    // }

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class,args);
    }
}
