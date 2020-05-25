package com.edu.juc;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class TestThread1 {
    public static void main(String[] args) throws InterruptedException {
        test2();
    }

    private static void openDoor() throws InterruptedException {

        AtomicInteger clock = new AtomicInteger();
        for (int i = 5; i < 10; i++) {
            new Thread(() -> {

                System.out.println("这个人是"+clock.getAndIncrement() + "点来到图书馆");

            }, String.valueOf(i)).start();
        }

        System.out.println("我是图书馆管理员 我"+clock+"来开门了 ！！！！" );
    }

    private static void lockDoor() {
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t" + "看完书了 走出了图书馆");
            }, String.valueOf(i)).start();
        }
        System.out.println();
    }
    private static void countDownLatchTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(5);

        for (int i = 0; i < 5; i++) {
            new Thread(() ->{
                System.out.println(Thread.currentThread().getName()+"\t"+"看完书了 走出了图书馆");
                countDownLatch.countDown();
            },String.valueOf(i)).start();
        }
        countDownLatch.await();
        System.out.println("我是图书馆管理员 我来锁门了 ！！！！");
    }



    private static void semaphoreTest() {
        Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 6; i++) {
            new Thread(()->{
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName()+"\t 占用了一个停车位");
                    // 占用停车位的时间
                    int time = new Random().nextInt(3) + 1;
                    TimeUnit.SECONDS.sleep(time);
                    System.out.print(time);
                    System.out.println(Thread.currentThread().getName()+"\t 溜了 溜了！");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release();
                }
            }).start();
        }
    }


    private static void cyclicBarrierTest(){
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5, () ->{
            // 人齐开饭
            System.out.println("ok 开饭了 ");
        });
        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"到了 ！");
                try {
                    // 人不齐就是先等着
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }
    }

    private static void test2(){
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5, ()-> {
            System.out.println("kaifan");
        });
        for (int i = 0; i < 5; i++) {
            new Thread(() ->{
                try {
                    System.out.println(Thread.currentThread().getName() + "到了");
                    cyclicBarrier.await();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();

        }

    }
}
