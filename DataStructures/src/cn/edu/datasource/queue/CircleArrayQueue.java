package cn.edu.datasource.queue;

/**
 * 循环队列实现完成 下一步是添加泛型
 */
public class CircleArrayQueue {
    //循环对列判断满的条件 是 （rear +1） % maxsize = front
    // 循环对列的有效数据的个数为(rear- front + maxSize) % maxSize
    //  循环队列为空的条件是：rear == front
    // front 指向的是队列中第一个元素 初始值就是0
    // rear 指向的就是最后的一个元素的下一个位置 rear 初始值为0 队列中空出一个位置
    private int maxSize = 10;
    private int[] array = new int[maxSize];
    private int rear = 0;
    private int front = 0;


    // 判断队列 是否是为满
    public boolean isFull(){

        return (rear +1) % maxSize == front;
    }
    //  判断数列是否为空
    public boolean isEmpty(){
        return rear == front;
    }
    public void add(int element){
        if (!isFull()) {
            array[rear] = element;
            rear = (rear + 1) % maxSize;
        }else{
            System.out.println("队列已满");
        }
    }
    // 取出数据
    public int getQueue(){
        if (!isEmpty()){
            // front指向的是队列中的第一个元素 先将数据取出
            int delElement = array[front];
            front = (front + 1) % maxSize;
            return delElement;
        }else{
            throw new RuntimeException("队列为空不能 删除数据！");
        }


    }
    //显示数据 就是循环打印数据
    public void show(){
        // 首先是判断是否是为空
        if(!isEmpty()){
            // 从front开始遍历 遍历多少个？ 就是遍历有效的个数
            // 队列中有效的个数 ： （rear - front + maxSize）% maxSize
            for (int i = front; i < front + (rear - front + maxSize)% maxSize; i++) {
                System.out.println(array[ i % maxSize]);
            }
        }else{
            System.out.println("队列为空");
        }

    }
    //显示队首元素
    public int showFront(){
        return array[front];
    }
    // 显示的是最后的一个数据的位置 （在数组中的位置）
    public int showRear(){
        return (rear - 1 +maxSize) % maxSize;
    }
    public static void main(String[] args) {// 使用数组来模拟循环队列
        CircleArrayQueue arrayQueue = new CircleArrayQueue();
        for (int i = 0; i < 9; i++) {
            arrayQueue.add(i);
        }
        arrayQueue.getQueue();
        arrayQueue.getQueue();
        arrayQueue.getQueue();
        arrayQueue.getQueue();
        arrayQueue.add(10);
        arrayQueue.add(11);
        arrayQueue.add(12);;
        arrayQueue.show();
        System.out.println(arrayQueue.showRear());

    }
}
