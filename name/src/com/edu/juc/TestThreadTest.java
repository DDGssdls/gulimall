package com.itheima.test;

public class TestThreadTest {

    public static void main(String[] args) {
        ThreadTest threadTest = new ThreadTest();
        new Thread(() -> {
            for (int i = 0; i < 26; i++) {
                try {
                    threadTest.work1();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 26; i++) {
                try {
                    threadTest.work2();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

class ThreadTest{
    // 多线程操作就是三点 判断 干活 通知
    // 进行标志位的设定
    private boolean flag = false;
    private int num = 1;
    private char aChar ='a';
    public synchronized void work1() throws InterruptedException {
        while (flag != false){
            this.wait();
        }
        for (int i = 0; i < 2; i++) {
            System.out.println(num ++);
        }
        // 进行标志位的修改
        flag = true;
        this.notifyAll();
    }
    public synchronized  void work2() throws InterruptedException {
        while (flag != true){
            this.wait();
        }
        System.out.println(aChar);
        aChar += 1;
        // 进行通知
        flag = false;
        this.notifyAll();
    }
}
