package com.event.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 事件有三个部分:事件发布, 监听, 事件源
 * 事件测试
 * @author YZG
 */
@RestController
@RequestMapping("/publish/event")
public class PublishEvent {

    // 发布事件中的基类
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * localhost:8080/publish/event/execute
     */
    @RequestMapping(value = "execute")
    private void execute(){
        applicationEventPublisher.publishEvent(new MyApplicationEvent());
    }

    /**
     * 测试spring自带的事件
     */
    @RequestMapping(value = "eventStop")
    public void  eventStop(){
        System.exit(0);
    }
}
