package com.activemq.example.queueandtopic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;
import javax.jms.Topic;

/**
 * 消息生产者
 * @author yangzhiguo  2017/11/1.
 */
@Component
public class Publishr {

    private final JmsMessagingTemplate jmsMessagingTemplate;
    private final Queue queue;
    private final Topic topic;

    @Autowired
    public Publishr(JmsMessagingTemplate jmsMessagingTemplate, Queue queue, Topic topic) {
        this.jmsMessagingTemplate = jmsMessagingTemplate;
        this.queue = queue;
        this.topic = topic;
    }

    /**
     * P2P & Pub-Sub
     */
    public void send(){
        jmsMessagingTemplate.convertAndSend(queue,"生产者生产的点对点消息成果!");
        System.out.println("生产者生产点对点的消息成果!");

        jmsMessagingTemplate.convertAndSend(queue,"生产者生产的订阅&发布消息成果!");
        System.out.println("生产者生产的订阅&发布消息成果!");
    }
}
