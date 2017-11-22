package com.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * yangzhiguo on 2017/8/31.
 */
@Controller
public class Demo {
    @RequestMapping("/test/demo")
    public String index() {

        return "login";
    }
}
