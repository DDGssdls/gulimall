package com.edu.juc;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestSaleTicket {
    public static void main(String[] args) {
        Sale sale = new Sale();

        new Thread(() ->{
            for (int i = 0; i < 40; i++) {
                sale.sale();
            }
        }).start();
        new Thread(() ->{
            for (int i = 0; i < 40; i++) {
                sale.sale();
            }
        }).start();
        new Thread(() ->{
            for (int i = 0; i < 40; i++) {
                sale.sale();
            }
        }).start();
    }
}

class Sale{
    private int ticket = 30;
    Lock lock = new ReentrantLock();


    public void sale(){
        lock.lock();
            try {
                if (ticket> 0){
                    ticket --;
                    System.out.println(Thread.currentThread().getName()+"\t"+ticket);
                }
            }finally {
                lock.unlock();
            }

    }
}