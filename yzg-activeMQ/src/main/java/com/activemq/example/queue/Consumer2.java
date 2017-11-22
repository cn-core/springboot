package com.activemq.example.queue;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author yangzhiguo  2017/10/31.
 */
@Component
public class Consumer2 {

    @JmsListener(destination = "test.queue")
    public void reveiveMsg(String message) {
        System.out.println(">>>>>>>>>>>>>>>>>>>收到消息2:" + message);
    }
}
