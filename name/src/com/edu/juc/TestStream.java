package com.edu.juc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestStream {
    public static void main(String[] args) {
        List<Integer> integer = new ArrayList<>();
        integer.add(1);
        integer.add(2);
        integer.add(3);
        integer.add(4);
        integer.add(5);
        integer.add(6);
        integer.add(7);
        integer.add(8);
        integer.add(9);
        integer.stream()
                .filter(integer1 -> integer1 % 2 == 0)
                .filter(integer1 -> integer1 > 2)
                .map(integer1 -> integer1 +1 )
                .sorted((integer1, integer2) ->{ return integer2.compareTo(integer1);})
                .limit(2)
                .forEach(System.out::println);

    }
}
