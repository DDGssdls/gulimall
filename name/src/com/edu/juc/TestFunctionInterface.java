package com.edu.juc;


import javax.xml.ws.Provider;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class TestFunctionInterface {

    public static void main(String[] args) {
        /*Function<String, Object> function = new Function<String, Object>() {
            @Override
            public Object apply(String s) {
                return "null";
            }
        };
        Object string = function.apply("String");
        System.out.println(string);
        // 第一中就是Function函数性接口
        Function<String, Integer> function1 = s -> s.length();
        Integer integer = function1.apply("asdf");
        System.out.println(integer);
        // 第二种就是断定性接口
        Predicate<String> predicate = s -> s.isEmpty();
        boolean zhangsan = predicate.test("zhangsan");
        System.out.println(zhangsan);*/
        // 第三种就是消费型接口
        Consumer<String> consumer = s -> System.out.println(s.length());
        consumer.accept("string");
        // 第四种
        Supplier<String> provider = () ->  "";
        String s = provider.get();
        System.out.println(s);
    }
}
