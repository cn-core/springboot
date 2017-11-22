package com.idata3d.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author zhangpeng
 */
@SpringBootApplication
@EnableScheduling
public class SchedulerApplication
{
    public static void main(final String[] args)
    {
        SpringApplication.run(SchedulerApplication.class);
    }
}
