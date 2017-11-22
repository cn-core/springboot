package com.common.jmx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/MBean")
public class MBeanController
{

    @Autowired
    private AnnotationTestMBean annotationTestMBean;

    @RequestMapping(value = "mbean")
    public void mbean(String username)
    {
        annotationTestMBean.setName(username);

        /*for (int i = 0;i < 10000000;i++){
            User user = new User();
            System.out.println(user.toString());
        }*/
    }
}
