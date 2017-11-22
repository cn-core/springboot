package com.function.newinterface;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 在实际应用中,我们会有在项目服务启动的时候就去加载一些数据或作一些事情的需求
 *  为了解决这样的问题,spring boot为我们提供了一个方法,通过实现接口CommandLineRunner来实现
 * @author yangzhiguo  2017/10/25.
 */
@Component
@Order(value = 1)
public class MyStartupRunner2 implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println(">>>>>>>>>>>>服务启动执行,执行加载数据等操作22222222<<<<<<<<<<<");
    }
}
