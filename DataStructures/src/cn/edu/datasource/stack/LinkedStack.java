package cn.edu.datasource.stack;

import cn.edu.datasource.linkedlist.Node;
import cn.edu.datasource.linkedlist.SingleLinkedList;

public class LinkedStack {
    //  使用单向链表进行栈的模拟
    private SingleLinkedList singleLinkedList;
    public int no;

    public LinkedStack(){
        singleLinkedList = new SingleLinkedList();
    }
    public void push(int num){
        singleLinkedList.add(new Node(num, null, null));
        no = num;
    }
    public void pop(){
        singleLinkedList.delete(no);
    }
    public void peek(){
        System.out.println(no);
    }
    public void show(){
       singleLinkedList.reversePrint();
    }

    public static void main(String[] args) {
        LinkedStack linkedStack = new LinkedStack();
        for (int i = 0; i < 4; i++) {
            linkedStack.push(i);
        }
        linkedStack.peek();
        linkedStack.pop();
        linkedStack.peek();
        linkedStack.show();

    }
}


