package com.edu.juc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

public class TestThreadDemo1 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName());

            LockSupport.park();
            System.out.println("线程恢复！");
        });
        t1.start();
        TimeUnit.SECONDS.sleep(1L);
        System.out.println(Thread.currentThread().getName());
        LockSupport.unpark(t1);




    }

}
