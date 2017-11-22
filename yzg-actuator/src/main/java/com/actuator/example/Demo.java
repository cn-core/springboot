package com.actuator.example;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * YZG on 2017/9/27.
 */
@RestController
@RequestMapping("demo")
public class Demo {

    @RequestMapping(value = "test")
    public void test(){
        System.out.println("test");
    }
}
