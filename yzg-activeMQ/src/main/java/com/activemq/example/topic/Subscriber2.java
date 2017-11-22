package com.activemq.example.topic;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author yangzhiguo  2017/10/31.
 */
@Component
public class Subscriber2 {

    @JmsListener(destination = "test.topic",containerFactory = "topicListenerFactory")

    public void subscribe(String message) throws InterruptedException {
        System.out.println(">>>>>>>>>>>>>>>>>>>收到订阅的消息Topic2:" + message);
    }
}
