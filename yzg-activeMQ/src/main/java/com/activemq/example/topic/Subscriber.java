package com.activemq.example.topic;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * 订阅
 * @author yangzhiguo  2017/10/31.
 */
@Component
public class Subscriber {

    @JmsListener(destination = "test.topic",containerFactory = "topicListenerFactory")
    public void subscribe(String message) throws InterruptedException {
        System.out.println(">>>>>>>>>>>>>>>>>>>收到订阅的消息Topic1:" + message);
    }
}
