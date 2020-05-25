package com.edu.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TestVolatile {
    public static void main(String[] args) throws InterruptedException {
        //testVolatileCAS();
        MyData2 myData2 = new MyData2();
        new Thread(() ->{
            myData2.change();
        }).start();
        new Thread(() ->{
            myData2.change2();
        }).start();
        while (Thread.activeCount() > 2){
            Thread.yield();
        }
        System.out.println(myData2.x +"\t"+myData2.y);
    }
    // volatile就是 在使用内存屏障来进行指令的重排 同时也能进行数据的缓存的刷新

    private static void testVolatileCAS() {
        //saveOkOfVolatile();
        // 使用二十线程进行一千次的添加操作
        MyData myData = new MyData();
        for (int i = 0; i < 20; i++) {
            new Thread(() ->{
                for (int j = 0; j < 1000; j++) {

                    myData.addPlusPlus();
                    myData.addAtomic();
                }
            } ).start();
        }
        // 获取前面计算的值
        while (Thread.activeCount() > 2){
            Thread.yield();
        }
        System.out.println(myData.number);
        System.out.println(myData.atomicInteger);
    }

    private static void saveOkOfVolatile() {
        MyData myData = new MyData();

        Thread thread = new Thread(() -> {
            System.out.println("1");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myData.add();
            System.out.println("修改完成");
        });
        thread.start();


        while (myData.number == 0){

        }
        System.out.println("xiugai"+ myData.number);
    }


}

class MyData{
    volatile int number = 0;

    public  void add(){
        this.number = 10;
    }

    public  void addPlusPlus(){
        number ++;
    }

    AtomicInteger atomicInteger = new AtomicInteger();

    public void addAtomic(){
        atomicInteger.getAndIncrement();

    }
}
class MyData2{
    int a, b, x, y = 0;

    public void change(){
        x = a;
        b = 1;
    }
    public void change2(){
       y = b;
       a = 2;

    }
}
