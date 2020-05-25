package com.itheima.test;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class TestList {
    public static void main(String[] args){

        Mobile mobile = new Mobile();
        // 两个线程使用的是同一个对象。两个线程是一把锁！先调用的先执行！
        new Thread(()->mobile.sendEmail(),"A").start();


        new Thread(()->mobile.sendMS(),"B").start();
    }
}

// 手机，发短信，发邮件
class Mobile {
    // 被 synchronized 修饰的方法、锁的对象是方法的调用者、
    public synchronized void sendEmail() {
        System.out.println("sendEmail");
    }

    public synchronized void sendMS() {
        System.out.println("sendMS");
    }
}