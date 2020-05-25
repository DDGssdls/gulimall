package com.edu.juc;

public class TestThreadSync {
    private static final int num = 1000000;
    public static void main(String[] args) {
        long l = System.nanoTime();

        for (int i = 0; i < num; i++) {
            sync(i);
        }
         long l2 = System.nanoTime();
        System.out.println(l2 - l);

        long l3 = System.nanoTime();

        for (int i = 0; i < num; i++) {
            noSync(i);
        }
        long l4 = System.nanoTime();
        System.out.println(l4 - l3);

    }

    public synchronized static int sync(int i){
        i += 1;
        return i;
    }
    public static int noSync(int i){
        i += 1;
        return i;
    }
}
