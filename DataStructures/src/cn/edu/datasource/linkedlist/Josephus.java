package cn.edu.datasource.linkedlist;


import java.awt.*;
import java.time.temporal.Temporal;
import java.util.zip.CheckedOutputStream;

public class Josephus {

    public Node3 head;


    // 判空方法
    public boolean isEmpty(){
        return head == null;
    }

    // 使用循环链表实现约瑟夫问题
    public void add(Node3 node3){
        if (isEmpty()){
            head = node3;
            head.next = head;

        }else {
            Node3 temp = head;
            while (temp.next != head) {
                temp = temp.next;
            }
            temp.next = node3;
            node3.next = head;
            //System.out.println(temp.next.no);
        }
    }
    // show方法
    public void show(Node3 node3){
        Node3 temp = node3;
        while (temp.next != node3){
            System.out.println(temp);
            temp = temp.next;
        }
        //System.out.println(temp);
    }
    // delete 方法
    public void delete(Node3 node3){
        if (isEmpty()){
            System.out.println("链表为空 不能进行删除节点");
        }
        node3.next = node3.next.next;
    }

    public void josephus(int number, int nodeNum){
        int count = 1;
        if (nodeNum > 0){
            for (int i = 1; i <= nodeNum; i++) {
                add(new Node3(i,"zhangsan","lisi"));
            }
        }
        Node3 temp = head;
        while (nodeNum >= number){
            if (count + 1 == number){
                if (nodeNum == number){
                    System.out.print(temp.next.no);
                }else {
                    System.out.print(temp.next.no+"->");
                }
               temp.next = temp.next.next;
                nodeNum--;
                count = 1;
            }else {
                count++;
            }
            temp = temp.next;
        }
        System.out.println();
        for (int i = 0; i <number-1; i++) {
            System.out.println(temp);
            temp = temp.next;
        }
    }

    public static void main(String[] args) {
        Josephus josephus = new Josephus();
        /*for (int i = 0; i < 10; i++) {
            josephus.add(new Node3(i,"zhangsan","lisi"));
        }*/

        //josephus.show();
        josephus.josephus(3, 41);
        //josephus.show(josephus.head); //这里就不能使用show方法了 链表不是以前的链表了
        /*String property = System.getProperty("sun.arch.data.model");
        System.out.println(property); // 显示的是64位的java虚拟机*/
    }

}


// 定义一个节点类： 每一个Node对象 就是一个节点
class Node3{
    public int no;
    public String name;
    public String nickname;
    public Node3 next;// 指向的是下一个节点


    public Node3(int no, String name, String nickname) {
        this.no = no;
        this.name = name;
        this.nickname = nickname;

    }

    @Override
    public String toString() {
        return "Node3{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
