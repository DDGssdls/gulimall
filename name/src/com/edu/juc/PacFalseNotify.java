package com.edu.juc;

public class PacFalseNotify {

    public static void main(String[] args) {
        //1、虚假唤醒
        MyResourcesFalseNotify falseNotify = new MyResourcesFalseNotify();
        new Thread(()-> {for (int i = 0; i < 10; i++) {
            falseNotify.increment();
        }},"A").start();

        new Thread(()-> {for (int i = 0; i < 10; i++) {
            falseNotify.increment();
        }},"B").start();
        new Thread(()-> {for (int i = 0; i < 10; i++) {
            falseNotify.decrement();
        }},"C").start();

        new Thread(()-> {for (int i = 0; i < 10; i++) {
            falseNotify.decrement();
        }},"D").start();

    }
}

class MyResourcesFalseNotify {
    private int num = 0;

    //加1操作
    public synchronized void increment() {
        //1、判断
        try {
            while (num != 0) this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //2、干活
        num ++;
        System.out.print("线程" + Thread.currentThread().getName() + ":" + num + "\t");
        //3、通知
        this.notifyAll();
    }

    //减1操作
    public synchronized void decrement(){
        //1、判断
        try {
            while (num == 0) this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //2、干活
        num --;
        System.out.println("线程" + Thread.currentThread().getName() + ":" + num + "\t");
        //3、通知
        this.notifyAll();
    }
}

