package com.itheima.test;

import java.util.concurrent.atomic.AtomicInteger;

public class TestCasSync {
    private static AtomicInteger atomicInteger = new AtomicInteger();
    private static int i = 0;
    private static final int num = 1000000;
    public static void main(String[] args) {
        long l = System.nanoTime();
        for (int i1 = 0; i1 < num; i1++) {
            testSync();
        }
        long l1 = System.nanoTime();
        System.out.println(l1 - l);

        long l3 = System.nanoTime();
        for (int i1 = 0; i1 < num; i1++) {
            testCas();
        }
        long l4 = System.nanoTime();
        System.out.println(l4 - l3);
        System.out.println(i);
        System.out.println(atomicInteger.get());
    }

    private static synchronized void testSync(){
        i++;
    }
    private static void testCas(){
        atomicInteger.getAndIncrement();
    }
}
