package com.edu.juc;

public class TestThread {
    public static void main(String[] args) {
        ThreadDemo threadDemo = new ThreadDemo();
        new Thread(() ->{
            for (int i = 0; i < 5; i++) {
                try {
                    threadDemo.testPrint5();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() ->{
            for (int i = 0; i < 5; i++) {
                try {
                    threadDemo.testPrint10();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() ->{
            for (int i = 0; i < 5; i++) {
                try {
                    threadDemo.testPrint15();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}


class ThreadDemo{
    private int flag = 1;
    // 判断 操作 通知
    public synchronized void testPrint5() throws InterruptedException {
        while (flag != 1){
            this.wait();
        }
        System.out.println(Thread.currentThread().getName()+"a");
        flag = 2;
        notifyAll();
    }
    public synchronized void testPrint10() throws InterruptedException {
        while (flag != 2){
            this.wait();
        }
        System.out.println(Thread.currentThread().getName()+"b");
        flag = 3;
        notifyAll();
    }
    public synchronized void testPrint15() throws InterruptedException {
        while (flag != 3){
            this.wait();
        }
        System.out.println(Thread.currentThread().getName()+"c");
        flag = 1;
        notifyAll();
    }

}
