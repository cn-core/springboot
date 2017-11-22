package com.activemq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.jms.annotation.EnableJms;

/**
 * @author yangzhiguo  2017/10/31.
 */
@SpringBootApplication
@EnableJms
public class ActivemqApplication extends SpringBootServletInitializer{

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ActivemqApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ActivemqApplication.class,args);
    }
}
