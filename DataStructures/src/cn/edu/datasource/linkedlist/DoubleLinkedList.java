package cn.edu.datasource.linkedlist;

public class DoubleLinkedList {

    // 创建一个双向链表
    // 初始化虚拟的头结点
    private Node2 dummyHead = new Node2(0, null, null);
    // 首先是遍历的方法 ：show
    public void show(){
        if (isEmpty()){
            System.out.println("双向链表为空不能进行数据的遍历!");
        }
        Node2 temp = dummyHead.next;
        while (temp != null){
            System.out.println(temp);
            temp = temp.next;
        }

    }
    // 使用递归的 方式来进行遍历
    public void show(Node2 node2){
        if (node2 != null){
            show(node2.next);
            System.out.println(node2);

        }else{
            System.out.println("链表为空");
        }

    }
    // 判空方法
    public boolean isEmpty(){
        return dummyHead.next == null;
    }
    // 进行添加的方法
    public void add(Node2 node2){
        if (node2 == null){
            System.out.println("输入的节点的格式不正确");
        }
        /*dummyHead.next = node2;
        node2.previous = dummyHead;*/
        Node2 temp = dummyHead;
        while (temp.next != null){
            temp = temp.next;
        }
        // 找到最后的一个节点 插入新的节点
       /* temp.next = node2; 这两种方式是一致的都行
        node2.previous = temp;*/
        node2.previous =temp;
         temp.next = node2;

    }
    // 通过的node的no来进行修改信息
    public boolean update(int num, String name){
        if (isEmpty()){
            System.out.println("链表中的数据为空 不能进行修改");

        }
        boolean flag = false;
        Node2 temp = dummyHead.next;
        while (temp != null){
            if (temp.no == num){
                temp.name = name;
                flag = true;
                break;
            }
            temp = temp.next;
        }
        return flag;
    }
    // 从双向链表中删除节点
    public void delete(int num){
        if (isEmpty()){
            System.out.println("链表中没有数据");
        }
        Node2 temp = dummyHead.next;
        while(temp != null){
            if (temp.no == num){
                temp.previous.next = temp.next;
                // 这里是有问题的 如果是删除最后的一个节点 就会出现空指针异常 temp.next 为空 没有previous
                if (temp.next != null){
                    temp.next.previous = temp.previous;
                }

                break;
            }
            temp =temp.next;
        }
    }
    // 双向链表中按照no的值进行插入
    public void insert(Node2 node2){
        // 如果list为空 直接插入
        if (isEmpty()){
            add(node2);
        }else {
            // 如果不为空 进行遍历
            Node2 temp = dummyHead;
            while (temp.next != null) {
                if (node2.no == temp.next.no) {// 序号相同表示的是节点已经有了
                    //
                    System.out.println("节点的已经存在");
                    break;
                }
                if (node2.no < temp.next.no ) {// 只需要找到第一个比你序号大的节点
                    node2.next = temp.next;
                   node2.previous = temp.previous;
                    break;
                }

                temp = temp.next;
            }
            //  从循环中退出来表示的是已经到双向链表的末尾了 就直接在最后的一个节点上添加
            temp.next = node2;
            node2.previous = temp;
        }

    }





    public static void main(String[] args) {
        Node2 node1 = new Node2(1,"zhangsan", "zhangxiaosan");
        Node2 node2 = new Node2(2,"lisi", "zhangxiaosan");
        Node2 node3 = new Node2(3,"wangwu", "zhangxiaosan");
        Node2 node4 = new Node2(4,"zhaoliu ", "zhangxiaosan");
        Node2 node5 = new Node2(5,"zhaoliu ", "zhangxiaosan");
        Node2 node6 = new Node2(6,"zhaoliu ", "zhangxiaosan");
        Node2 node7 = new Node2(7,"zhaoliu ", "zhangxiaosan");
        DoubleLinkedList dbList = new DoubleLinkedList();
        dbList.insert(node2);
        dbList.insert(node1);
        dbList.insert(node3);
        dbList.insert(node6);
        dbList.insert(node7);
        dbList.insert(node4);
        dbList.insert(node5);

        /*dbList.add(node1);
        dbList.add(node2);
        dbList.add(node3);
        dbList.add(node4);
        dbList.add(node5);
        dbList.add(node6);
        dbList.add(node7);*/

        //dbList.show();
       /* dbList.show(node1);
        boolean lisi = dbList.update(3, "lisi");
        System.out.println(lisi);*/
       dbList.delete(7);
       dbList.show();
    }

}
class Node2{
    public int no;
    public String name;
    public String nickname;
    public Node2 next;// 指向的是下一个节点
    public Node2 previous;// 指向的是前一个节点
    public Node2(){

    }

    public Node2(int no, String name, String nickname) {
        this.no = no;
        this.name = name;
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "Node2{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
