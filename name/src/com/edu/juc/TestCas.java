package com.itheima.test;

import java.util.concurrent.atomic.AtomicInteger;

public class TestCas {

    public static void main(String[] args) {
        // 什么是cas cas 就是比较并交换 就是compareandswap的缩写
        // cas会出现的问题就是 aba问题： 解决上面的问题就是使用原子引用： 就是加上版本号 使用时间戳作为版本号
        // 就是使用atomicstampreference 就是带有一个时间戳类型的integer 能进行原子的更新

        AtomicInteger atomicInteger = new AtomicInteger(5);
        /*atomicInteger.getAndIncrement();
        atomicInteger.getAndDecrement();
        boolean b = atomicInteger.compareAndSet(5, 2019);
        int i = atomicInteger.get();
        System.out.println(b+ "\t   "+ i);*/

        new Thread(() ->{
            boolean b = atomicInteger.compareAndSet(5, 2019);
            System.out.println(b);

        }).start();
        new Thread(() ->{
            boolean b = atomicInteger.compareAndSet(5, 2020);
            System.out.println(b);
        }).start();

        int i = atomicInteger.get();
        System.out.println(i);
    }


}
