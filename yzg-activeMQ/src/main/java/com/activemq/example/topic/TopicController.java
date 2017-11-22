package com.activemq.example.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangzhiguo  2017/10/31.
 */
@RestController
@RequestMapping("/topic")
public class TopicController {

    @Autowired
    private Publish publish;

    @RequestMapping(value = "")
    public void topic(){
        for (int i = 0; i < 10; i++) {
            publish.sendMsg("test.topic","Topic Message:" + i);
        }
    }
}
