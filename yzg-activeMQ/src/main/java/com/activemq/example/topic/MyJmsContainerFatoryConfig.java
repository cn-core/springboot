package com.activemq.example.topic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;

import javax.jms.ConnectionFactory;

/**
 * Springboot内嵌的AcativeMQ默认是P2P,如果要用topic模式需要配置,
 * 还可以在application.properties中设置
 * @author yangzhiguo  2017/10/31.
 */
@Configuration
public class MyJmsContainerFatoryConfig {

    @Bean
    public JmsListenerContainerFactory topicListenerFactory(ConnectionFactory connectionFactory){
        SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
        factory.setPubSubDomain(true);
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }
}
