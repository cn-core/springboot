package com.devtools.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author YZG on 2017/9/29
 */
@RestController
@RequestMapping("/demo")
public class Demo {

    @Value("${profile}")
    private String profile;

    @RequestMapping(value = "test2")
    public String test2() {
        System.out.println(profile);
        return "test";
    }
}
