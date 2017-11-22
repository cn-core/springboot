package com.jpa.example;

import com.jpa.example.dao.UserRepository;
import com.jpa.example.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangzhiguo  2017/11/6.
 */
@RestController
@RequestMapping("/demo")
@Validated
public class ExampleController {

    private final UserRepository userManager;

    @Autowired
    public ExampleController(UserRepository userManager) {
        this.userManager = userManager;
    }

    @RequestMapping(value = "test")
    public User test(){
        // List<User> users = userManager.findByAddress("山西1");
        // User user = userManager.findByAddressAndUserName("山西","李四");
        // User user = userManager.findByAddressLike("山西");
        User user = userManager.findByUserNameIgnoreCase("Join");
        System.out.println(user.toString());
        return null;
    }
}
