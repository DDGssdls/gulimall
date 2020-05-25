package com.itheima.test;

import javax.print.attribute.standard.Finishings;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestExecutors {
    public static void main(String[] args) {
        ExecutorService executorService1 = Executors.newFixedThreadPool(3);// 传入的是工作线程的数量
        ExecutorService executorService2 = Executors.newSingleThreadExecutor();// 只有一个工作线程
        ExecutorService executorService = Executors.newCachedThreadPool();// 一个池子中有多个线程

        // 线程池的实现类就是 ThreadPoolExecutor
        try {
            for (int i = 0; i < 10; i++) {
                TimeUnit.SECONDS.sleep(1);
                executorService.execute(() ->{
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"在进行办理业务");
                });
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 线程池在使用完成之后需要进行释放
            executorService.shutdown();
        }
       


    }
}
