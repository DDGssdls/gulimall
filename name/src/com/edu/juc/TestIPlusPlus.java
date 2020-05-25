package com.itheima.test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestIPlusPlus {
    private  int num;
    private  int num1 = 0;
    private final Lock lock = new ReentrantLock();
    public static void main(String[] args) throws InterruptedException {
/*        TestIPlusPlus testIPlusPlus = new TestIPlusPlus();
        for (int i = 0; i < 100; i++) {
            testIPlusPlus.num ++;
            testIPlusPlus.num1 ++;

        }
        final int[] c = {2};
        System.out.println(testIPlusPlus.num);
        System.out.println(testIPlusPlus.num1);

        Thread thread = new Thread(() -> {
            System.out.println();

        });
        thread.interrupt();*/
        new Thread(() ->{
            try {
                testExe();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        TimeUnit.SECONDS.sleep(2);
        new Thread(() ->{
            testExe2();
        }).start();

    }

    private static synchronized void testExe() throws Exception {
        System.out.println("xiancheng kaishi le ");
        throw  new Exception();

    }
    private static synchronized void testExe2(){
        System.out.println("kaishile ");
    }
}
