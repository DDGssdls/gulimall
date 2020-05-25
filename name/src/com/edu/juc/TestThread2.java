package com.edu.juc;

public class TestThread2 {
    public static void main(String[] args) {
        TestThreadDemo threadDemo = new TestThreadDemo();
        new Thread(() ->{
            try {
                for (int i = 0; i <5 ; i++) {
                    threadDemo.increase();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"Thread1").start();
        new Thread(() ->{
            try {
                for (int i = 0; i < 5; i++) {
                    threadDemo.decrease();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"Thread2").start();
        new Thread(() ->{
            try {
                for (int i = 0; i < 5; i++) {
                    threadDemo.increase();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"Thread3").start();
        new Thread(() ->{
            try {
                for (int i = 0; i < 5; i++) {
                    threadDemo.decrease();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"Thread4").start();
    }
}


class TestThreadDemo{
    private int number = 0;

    public synchronized  void increase() throws InterruptedException {
        while (number != 0){
            this.wait();
        }
        System.out.println(Thread.currentThread().getName() +"\t" +number);
        number ++;

        this.notifyAll();
    }
    public synchronized  void decrease() throws InterruptedException {
        while (number != 1){
            this.wait();
        }
        System.out.println(Thread.currentThread().getName() +"\t" +number);
        number --;

        this.notifyAll();
    }
}
