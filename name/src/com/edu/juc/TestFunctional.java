package com.itheima.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class TestFunctional {

    public static void main(String[] args) {
        Employee employee = getEmployee(() -> new Employee());
        System.out.println(employee);
    }
    private static void payMoney(int baozi, Consumer<Integer> consumer){
        consumer.accept(baozi);
    }

    private static List<String> screenName(List<String> list, Predicate<String> predicate){
        List<String> nameList = new ArrayList<>();
        for (String s : list) {
            if (predicate.test(s)){
                nameList.add(s);
            }
        }
        return nameList;
    }
    // 获取字符串的长度
    private static int getLength(String string, Function<String , Integer> function){
        return function.apply(string);
    }
    private static Employee getEmployee(Supplier<Employee> supplier){
        Employee employee = supplier.get();
        return employee;
    }


}
class Employee{
    String name;
    String password;
}
