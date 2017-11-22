package com.event.example;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 定义一个事件监听器
 * @author YZG
 */
@Component
public class MyApplicationListener2 {

    /**
     * 参数任意(为Object)的时候所有事件都会监听到所有,
     * 该参数事件,或者其子事件(子类)都可以接收到
     * @param event
     */
    @EventListener
    public void event(MyApplicationEvent event){
        System.out.println("事件监听器2>接收到事件:" + event.getClass());
    }
}
