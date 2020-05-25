package com.itheima.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TestIterator {
    public static void main(String[] args) {
        testCompare(1, (integer) -> 1- 3);

    }
    private static void testCompare(Integer integer, Comparable<Integer> comparable){
        System.out.println(comparable.compareTo(integer));
    }
}
