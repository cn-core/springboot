package com.async.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 异步任务(@Async)测试
 * @author YZG
 */
@RestController
@RequestMapping("/async")
public class AsyncTaskController {

    private final AsyncTask asyncTask;

    @Autowired
    public AsyncTaskController(AsyncTask asyncTask) {
        this.asyncTask = asyncTask;
    }

    /**
     * Async测试  注:在同一个类中,方法调用时在类体内执行的,Spring无法截获这个方法调用,
     *      所以异步执行@Async没有作用
     * @return 任务执行时间
     */
    @RequestMapping(value = "doTask")
    public String doTask() throws InterruptedException {
        long time1 = System.currentTimeMillis();
        asyncTask.task1();
        asyncTask.task2();
        asyncTask.task3();
        long time2 = System.currentTimeMillis();
        return "task任务总耗时:"+(time1 - time2)+"ms";
    }

    /**
     * 异步任务什么时候执行完成,执行结果等判定
     */
    @RequestMapping(value = "doTaskResult")
    public String doTaskResult() throws InterruptedException, ExecutionException {
        long time = System.currentTimeMillis();
        Future<String> stringFuture1 = asyncTask.task1Result();
        Future<String> stringFuture2 = asyncTask.task2Result();
        Future<String> stringFuture3 = asyncTask.task3Result();
        for (;;){
            // 获取执行后的结果
            // System.out.println(stringFuture1.get());
            // System.out.println(stringFuture2.get());
            // System.out.println(stringFuture3.get());
            if (stringFuture1.isDone() && stringFuture2.isDone() && stringFuture3.isDone()) {
                // 三个任务都调用完成,退出循环等待
                break;
            }
            Thread.sleep(1000);
        }
        long time2 = System.currentTimeMillis();
        return "task任务总耗时:" + (time2 - time) + "ms";
    }
}
