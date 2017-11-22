package com.webmvc.config;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 手动设置静态资映射
 *      注:修改后不起作用,可能启动时没有clean
 * @author YZG
 */
// @Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    /**
     * 自定义资源映射
     */
    // @Override
    // public void addResourceHandlers(ResourceHandlerRegistry registry) {
    //     registry.addResourceHandler("/myres/**").addResourceLocations("classpath:/myres/");
    // }
    /**
     * 使用外部目录
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/myres/**")
                .addResourceLocations("file:D:/myres/");
    }
}
