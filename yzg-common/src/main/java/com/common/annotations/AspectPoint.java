package com.common.annotations;

import java.lang.annotation.*;

/**
 * 自定义切入点注解,作用在方法上
 * @author yangzhiguo
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface AspectPoint
{

}
