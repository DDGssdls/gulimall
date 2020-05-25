package com.edu.juc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadDemo3 {
    public static void main(String[] args) {

        TestThreadDemo3 testThreadDemo3 = new TestThreadDemo3();

        new Thread(() ->{
            try {
                for (int i = 0; i <5 ; i++) {

                    testThreadDemo3.increase();

                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"Thread1").start();
        new Thread(() ->{
            try {
                for (int i = 0; i < 5; i++) {
                    testThreadDemo3.decrease();

                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"Thread2").start();
        new Thread(() ->{
            try {
                for (int i = 0; i < 5; i++) {
                    testThreadDemo3.increase();

                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"Thread3").start();
        new Thread(() ->{
            try {
                for (int i = 0; i < 5; i++) {
                    testThreadDemo3.decrease();

                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"Thread4").start();
    }


}

class TestThreadDemo3{
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    Condition condition2 = lock.newCondition();
    private int number = 0;


    public void increase() throws InterruptedException {
        lock.lock();
        if (number != 0){
            condition.await();
        }

        System.out.println(Thread.currentThread().getName() +"\t" +number);

        number ++;

        condition2.signalAll();
        lock.unlock();
    }
    public void decrease() throws InterruptedException {
        lock.lock();
        if (number != 1){
            condition2.await();
        }

        System.out.println(Thread.currentThread().getName() +"\t" +number);
        number --;

        condition.signalAll();
        lock.unlock();
    }
}
