package com.itheima.test;

public class TestNull {

    public static void  haha(){
        System.out.println("haha");
    }

    public static void main(String[] args) {
        TestNull testNull = null;
        testNull.haha();
        ((TestNull)null).haha();
    }
}
