package com.itheima.test;

import javax.lang.model.element.VariableElement;
import javax.swing.text.Style;
import java.util.concurrent.TimeUnit;

public class TestLock {
    public static void main(String[] args) {
        myLock myLock = new myLock();
        new Thread(() ->{
            try {
                myLock.test1();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() ->{
            try {
                myLock.test2();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }
}
class myLock{
    Object object1 = new Object();
    Object object2 = new Object();

    public void test1() throws InterruptedException {
        synchronized (object1){
            TimeUnit.SECONDS.sleep(2);
            synchronized (object2){
                System.out.println("1");

            }
        }
    }
    public void test2() throws InterruptedException {
        synchronized (object2){
            TimeUnit.SECONDS.sleep(2);
            synchronized (object1){
                System.out.println("2");
            }
        }
    }
}
