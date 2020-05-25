package com.edu.juc;

import java.time.temporal.ValueRange;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TestReadLock {
    public static void main(String[] args) {
        MyCache myCache = new MyCache();

        for (int i = 0; i < 5; i++) {
            final int temInt = 0;
            new Thread(() ->{
                myCache.put(Thread.currentThread().getName(),temInt);
            }).start();
        }
        for (int i = 0; i < 5; i++) {
            new Thread(() ->{
                myCache.get(Thread.currentThread().getName());
            }).start();
        }

    }
}
class MyCache{
    private volatile Map<String, Object> map = new HashMap<>();
    ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public void put(String key, Object value){
        try {
            readWriteLock.writeLock().lock();
            System.out.println(Thread.currentThread().getName()+ "\t写入数据");
            // 进行网络延时的模拟
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.put(key, value);
            System.out.println(Thread.currentThread().getName()+ "\t写入完成");
        }finally {
            readWriteLock.writeLock().unlock();
        }

    }
    public void get(String key){
        try {
           //readWriteLock.readLock().lock();
            System.out.println(Thread.currentThread().getName()+ "读出数据");
            // 进行网络延时的模拟
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.get(key);
            System.out.println(Thread.currentThread().getName()+ "读出完成");
        }finally {
          // readWriteLock.readLock().unlock();
        }

    }
}
