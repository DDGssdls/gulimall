package com.itheima.test;



public class TestInnerClass {
    public static void main(String[] args) {
        Outer.Inner inner = new Outer. Inner();//创建内部类对象
        Outer.Inner.shou();
        String a = "123";
        String b= "234";
        Integer integer = Integer.valueOf(a);

        int i1 = Integer.parseInt(b);

    }
}
    class Outer{
        private static int age = 10;
        public void testOuter() {
            System.out.println("");
        }
       static class Inner{//inner 是内部类  可以使用外部类的属性和方法
            public static void shou() {
                System.out.println(Outer.age);
            }
        }
    }