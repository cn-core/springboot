package com.activemq.example.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Queue模式
 * @author yangzhiguo  2017/10/31.
 */
@RestController
@RequestMapping("/queue")
public class QueueController {

    @Autowired
    private Producer producer;

    @RequestMapping(value = "")
    public void queue(){
        final String destination = "test.queue";

        for (int i = 0; i < 10; i++) {
            producer.sendMsg(destination, "Queue Message:" + i);
        }
    }
}
