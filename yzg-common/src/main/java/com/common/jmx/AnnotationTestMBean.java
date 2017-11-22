package com.common.jmx;

import org.springframework.jmx.export.annotation.*;
import org.springframework.stereotype.Component;


@Component
@ManagedResource(objectName = "beans:name=lionbuleTest",description = "My Managed Bean")
public class AnnotationTestMBean
{
    private String name;
    private Integer age;

    @ManagedAttribute
    public void setName(String name)
    {
        System.out.println(name);
        this.name = name;
    }

    @ManagedAttribute
    public String getName()
    {
        return this.name;
    }

    public Integer getAge()
    {
        return age;
    }

    public void setAge(Integer age)
    {
        this.age = age;
    }

    @ManagedOperation(description = "Add two numbers")
    @ManagedOperationParameters({
    @ManagedOperationParameter(name = "x",description = "The first number"),
    @ManagedOperationParameter(name = "y", description = "The second number")})
    public Integer add_1(Integer x,Integer y)
    {
        System.out.println(x + y);
        return  x + y;
    }

    @ManagedOperation
    public Integer add_2(Integer x,Integer y)
    {
        System.out.println(x + y);
        return  x + y;
    }

    public void dontExposeMe()
    {
        throw new RuntimeException();
    }
}
