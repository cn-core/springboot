package com.mybatis.example;

import com.mybatis.example.mapper.Test;
import com.mybatis.example.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yangzhiguo  2017/11/3.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private Test test;

    @RequestMapping(value = "info")
    public List<User> info(){
        List<User> demo = test.findUser();
        demo.forEach(data -> System.out.println(data.toString()));
        return demo;
    }
}
