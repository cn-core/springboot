package com.common.annotations;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 通过Aspectj(AOP)配合自定义注解实现某功能
 * @author yangzhiguo
 */
@Component
@Aspect
public class Aspects
{
    private final static Log log = LogFactory.getLog(Aspects.class);

    /**
     * 前置通知
     * @within(com.idataway.controller) 指定切入点,在执行指定的***之前执行前置通知
     */
    @Before("@annotation(com.common.annotations.AspectPoint)")
    public void before(JoinPoint joinPoint)
    {
        log.info("执行某些控制!");
    }
}
