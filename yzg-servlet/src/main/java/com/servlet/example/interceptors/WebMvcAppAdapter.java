package com.servlet.example.interceptors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 添加自定义拦截器
 * @author yangzhiguo  2017/10/24.
 */
@Configuration
public class WebMvcAppAdapter extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns() 用于添加拦截规则
        // excludePathPatterns() 用于排除拦截
        registry.addInterceptor(new MyInterceptors()).addPathPatterns("/**");
    }
}
