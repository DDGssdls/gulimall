package cn.edu.datasource.linkedlist;

// 定义一个节点类： 每一个Node对象 就是一个节点
public class Node{
    public int no;
    public String name;
    public String nickname;
    public Node next;// 指向的是下一个节点
    public Node(){

    }

    public Node(int no, String name, String nickname) {
        this.no = no;
        this.name = name;
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "Node{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}