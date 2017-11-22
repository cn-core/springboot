package com.activemq.example.queueandtopic;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * 消费者
 * @author yangzhiguo  2017/11/1.
 */
@Component
public class Consumer {

    @JmsListener(destination = "test.queue")
    public void receiveQueue(String text){
        System.out.println("消费者=来源于生产者的:" + text);
    }

    @JmsListener(destination = "test.topic")
    public void revceiveSub1(String text){
        System.out.println("消费者Consumer1=" + text);
    }

    @JmsListener(destination = "test.topic")
    public void revceiveSub2(String text){
        System.out.println("消费者Consumer2=" + text);
    }
}
