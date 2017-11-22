package com.activemq.example.controller;

import com.activemq.example.queueandtopic.Publishr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Queue&Topic模式
 * @author yangzhiguo  2017/11/1.
 */
@RestController
@RequestMapping("/queue_topic")
public class QueueTopicController {

    @Autowired
    private Publishr publish;

    @RequestMapping(value = "queue")
    public void queue() {
        publish.send();
    }
}
