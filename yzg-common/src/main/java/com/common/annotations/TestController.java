package com.common.annotations;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangzhiguo
 */
@RestController
@RequestMapping("/aspect")
public class TestController
{
    private final Log log = LogFactory.getLog(TestController.class);

    @RequestMapping(value = "points")
    @Cacheable(value = "myres",key = "#id")
    public String points(String id){
        System.out.println(id);
        String tmp = "杨治国";
        return tmp;
    }
}
