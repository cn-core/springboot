package com.devtools;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author YZG on 2017/9/28.
 */
@SpringBootApplication
public class DevtoolsApplication {//extends SpringBootServletInitializer{

    /*@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(DevtoolsApplication.class);
    }*/

    public static void main(String[] args) {
        SpringApplication.run(DevtoolsApplication.class,args);
    }
}
