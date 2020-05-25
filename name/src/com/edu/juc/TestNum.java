package com.itheima.test;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;

public class TestNum implements Comparable<String>, Comparator<String> {
    public int a = 3;
    public static void main(String[] args) {
        /*首先就是进行注释的测试  非常的简单就是三种注释一种就是单行注释 一种就是多行注释 最后的一种就是文档注释*/
// 这是就是最简单的单行注释
        /*
         这种注释能作为行间注释 和多行注释两种使用方式
        * */
        /**
         指定的是说明行的文字 说明性的文字
         我就想知道的是变量一定是需要复制吗 但是不一定就是 需要赋值 也能是不使用赋值 直接使用 就是变量成员 和 静态变量 和 成员变量

         */

        boolean b= true? false: true == true? false: true;
        System.out.println(b);
        String string = "We Are Happy";
        String s = replaceSpace(new StringBuffer(string));
        System.out.println(s);

    }
    @Before
    public void testMethod(){
        System.out.println("这是before");
    }
    @BeforeClass
    public  void testMethod1(){// 需要注意的就是要是使用beforeclass 和 afterclass 就是 需要方法是静态的 也就是使用static进行修饰的
        System.out.println("这是beforeclass");
    }
    @Test
    public void testTest(){
        System.out.println("这是test");
    }


    @Override
    public int compareTo(String o) {
        return 0;
    }

    @Override
    public int compare(String o1, String o2) {
        return 0;
    }
    public static String replaceSpace(StringBuffer str) {
        return  str.toString().replaceAll("\\ ", "%20");
    }

    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        ArrayList arr = new ArrayList<Integer>();
        if (listNode.next != null) {
            printListFromTailToHead(listNode.next);
        }
        arr.add(listNode.val);

        return arr;
    }

    public ListNode solution(ListNode head , int k){
        if(head == null || k == 0){
            return head;
        }
        ListNode temp = head;
        for (int i = 0; i < k; i++) {
            if(head == null){
               return  null;
            }else{
               head = head.next;
            }
        }
        while (head != null){
            head = head.next;
            temp = temp.next;
        }

        return temp;
    }
}
class ListNode {
      int val;
      ListNode next = null;
      ListNode(int val) {
           this.val = val;
      }
}
