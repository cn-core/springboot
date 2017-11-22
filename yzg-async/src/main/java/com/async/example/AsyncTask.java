package com.async.example;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

/**
 * @author YZG
 */
@Component
public class AsyncTask {

    @Async
    void task1() throws InterruptedException {
        long time1 = System.currentTimeMillis();
        Thread.sleep(1000);
        long time2 = System.currentTimeMillis();
        System.out.println("task1任务耗时:" + (time1 - time2) + "ms");
    }

    @Async
    void task2() throws InterruptedException {
        long time1 = System.currentTimeMillis();
        Thread.sleep(2000);
        long time2 = System.currentTimeMillis();
        System.out.println("task2任务耗时:" + (time1 - time2) + "ms");
    }

    @Async
    void task3() throws InterruptedException {
        long time1 = System.currentTimeMillis();
        Thread.sleep(3000);
        long time2 = System.currentTimeMillis();
        System.out.println("task3任务耗时:" + (time1 - time2) + "ms");
    }

    // =====================异步任务什么时候执行完,执行结果==============================

    @Async
    Future<String> task1Result() throws InterruptedException {
        long time1 = System.currentTimeMillis();
        Thread.sleep(1000);
        long time2 = System.currentTimeMillis();
        System.out.println("task1任务耗时:" + (time1 - time2) + "ms");
        return new AsyncResult("task1执行完毕");
    }

    @Async
    public Future<String> task2Result() throws InterruptedException {
        long time1 = System.currentTimeMillis();
        Thread.sleep(2000);
        long time2 = System.currentTimeMillis();
        System.out.println("task2任务耗时:" + (time1 - time2) + "ms");
        return new AsyncResult("task2执行完毕");
    }

    @Async
    public Future<String> task3Result() throws InterruptedException {
        long time1 = System.currentTimeMillis();
        Thread.sleep(3000);
        long time2 = System.currentTimeMillis();
        System.out.println("task3任务耗时:" + (time1 - time2) + "ms");
        return new AsyncResult("task3执行完毕");
    }
}
