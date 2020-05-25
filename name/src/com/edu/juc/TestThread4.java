package com.edu.juc;

public class TestThread4 {
    public static void main(String[] args) throws InterruptedException {
        MyResource resource = new MyResource();
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                resource.test1();
            }

        });
        thread.start();
        thread.join();
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 5 ; i++) {

                resource.test2();
            }
        });
        thread2.start();
        thread2.join();
        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {

                resource.test3();
            }
        });
        thread3.start();
        thread3.join();

    }
}

class MyResource{
    public synchronized  void test1(){
        System.out.println("a");
    }
    public synchronized  void test2(){
        System.out.println("b");
    }
    public synchronized  void test3(){
        System.out.println("c");
    }
}
