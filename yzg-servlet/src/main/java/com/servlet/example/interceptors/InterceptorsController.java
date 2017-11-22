package com.servlet.example.interceptors;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangzhiguo  2017/10/24.
 */
@RestController
@RequestMapping("/interceptor")
public class InterceptorsController {

    @RequestMapping(value = "demo")
    public String demo(){
        return "Success";
    }
}
