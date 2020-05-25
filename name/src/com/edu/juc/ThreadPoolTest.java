package com.edu.juc;

import java.util.concurrent.*;

public class ThreadPoolTest {

    public static void main(String[] args) {
        threadPoolExecutor();
    }

    public static void threadPoolExecutor(){
        ExecutorService threadPool = new ThreadPoolExecutor(
                2,
                5,
                300L,
                TimeUnit.MICROSECONDS,
                new LinkedBlockingQueue<>(4),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        try {
            // 队列  RejectedExecutionException 拒绝策略
            for (int i = 1; i <= 10; i++) {
                // 默认在处理
                threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName()+" running....");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }

}
