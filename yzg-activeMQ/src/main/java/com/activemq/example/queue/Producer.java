package com.activemq.example.queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;


/**
 * @author yangzhiguo  2017/10/31.
 */
@Service("producers")
public class Producer {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void sendMsg(String destionationName,String message){
        System.out.println(">>>>>>>>>>>>>>发送消息:" + message);
        Destination destination = new ActiveMQQueue(destionationName);
        jmsMessagingTemplate.convertAndSend(destination,message);
    }
}
