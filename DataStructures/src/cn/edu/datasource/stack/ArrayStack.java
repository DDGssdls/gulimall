package cn.edu.datasource.stack;

public class ArrayStack {
    //  使用数组来模拟栈
    private int[] arr;
    private int size;

    public ArrayStack(){
        arr = new int[4];
    }
    public ArrayStack(int num){
        arr = new int[num];
    }

    public boolean isEmpty(){
        return size == 0;
    }
    public void push(int num){
        arr[size] = num;
        size++;
    }
    public int pop(){
        int temp = arr[size - 1];
        arr[size -1] = 0;
        size --;
        return temp;
    }
    public int peek(){
        return arr[size - 1];
    }
    public void show(){
        for (int i = size -1; i >= 0; i--) {
            System.out.print(arr[i] + "\t");
        }
    }


    public static void main(String[] args) {
        ArrayStack arrayStack = new ArrayStack();
        for (int i = 0; i < 3; i++) {
            arrayStack.push(i);
        }
        int peek = arrayStack.peek();
        boolean empty = arrayStack.isEmpty();
        System.out.println(empty);
        System.out.println(peek);
        arrayStack.show();

    }
}
