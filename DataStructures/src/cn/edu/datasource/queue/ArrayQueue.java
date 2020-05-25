package cn.edu.datasource.queue;

import java.util.Arrays;
// 使用的是数组来模拟队列 但是不是循环队列
public class ArrayQueue {
    // 使用数组来模拟队列
    private int maxSize = 10;// 最大容量
    private int[] arr;// 数组
    private int front = -1;// 头指针
    private int rear =0;// 尾指针
    // 使用数组来模拟队列 先进先出
    public ArrayQueue(){
        arr =  new int[maxSize];
    }
    public ArrayQueue(int size){
        maxSize = size;
        arr = new int[maxSize];
    }
    public boolean isEmpty(){
        return front == rear;
    }
    public int size(){
        return front + 1 - rear;
    }
    public void add(int count){
        if(!isFull()){
            arr[++front] = count;

        }else{
            System.out.println("队列已满！");
        }

    }
    public boolean isFull(){
        return front +1  == maxSize;
    }
    public int remove(){
        if(!isEmpty()){

            int count = arr[rear];
            for (int i = rear; i < front  ; i++) {
                arr[i] = arr[i+1];
            }
            arr[front--] = 0;
            return count;
        }else{
            throw new IllegalArgumentException("队列为空");
        }
    }
    public void show(){
        System.out.println(Arrays.toString(arr));
    }



}
