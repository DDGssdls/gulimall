package com.edu.juc;

import java.util.HashMap;

public class ThreadLocalTest {
    private MyThreadLocalTest<String> threadLocal = new MyThreadLocalTest<>();
    private ThreadLocal<String> threadLocal2 = new ThreadLocal<>();
    public static void main(String[] args) {
        Thread[] threads = new Thread[3];
        for (int i = 0; i < 3; i++) {
            new Thread(new ThreadLocalTest().new MyThreadLocal(i)).start();
        }

    }


     class MyThreadLocal implements Runnable{
        private int id;

        public MyThreadLocal(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            threadLocal.set("线程"+ id);
            System.out.println(Thread.currentThread().getName() +"线程编号 "+threadLocal.get());
        }
    }

     class MyThreadLocalTest<T>{
        private HashMap<Thread, T> MyTL = new HashMap<>();

        public void set(T i){
            MyTL.put(Thread.currentThread(), i);
        }
        public T get(){
            return MyTL.get(Thread.currentThread());
        }
    }
}
