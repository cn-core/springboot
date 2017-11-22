package com.activemq.example.queueandtopic;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yangzhiguo  2017/11/1.
 */
@Configuration
public class ActiveMqConfiguration {

    /**
     * P2P
     * point to point
     */
    @Bean
    public ActiveMQQueue queue(){
        return new ActiveMQQueue("test.queue");
    }

    /**
     * PUB TO SUB
     * publisher-subscriber
     */
    @Bean
    public ActiveMQTopic topic(){
        return new ActiveMQTopic("test.topic");
    }
}
