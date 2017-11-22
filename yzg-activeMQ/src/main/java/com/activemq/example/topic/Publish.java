package com.activemq.example.topic;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Destination;

/**
 * 发布
 * @author yangzhiguo  2017/10/31.
 */
@Component
public class Publish {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void sendMsg(String destinationName,final String message){
        Destination destination = new ActiveMQTopic(destinationName);
        System.out.println("============>>>>> 发布topic消息:" + message);
        jmsMessagingTemplate.convertAndSend(destination,message);
    }

}
