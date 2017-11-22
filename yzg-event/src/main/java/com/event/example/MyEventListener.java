package com.event.example;

import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author YZG
 */
@Component
public class MyEventListener {

    @EventListener
    public void eventStop(ContextClosedEvent event){
        System.out.println("应用停止事件......:" + event.getSource());
    }
}
