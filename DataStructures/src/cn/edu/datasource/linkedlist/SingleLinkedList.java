package cn.edu.datasource.linkedlist;


// P 24
public class SingleLinkedList {
    // 初始化一个头结点 ；头结点不能动 不存放具体的数据
    private Node dummyHead = new Node(0,null,null);


    //添加节点到单项列表中
    public void add(Node node){
        // 如果不考虑节点的顺序：
        // 1  首先是找到链表的最后的节点 2 将最后的节点的next域指向新的节点 3 但是头结点不能动 因此需要一个辅助的节点
        Node temp = dummyHead;
        // 主要tem.next 不为空 就一致的遍历下去
        while(temp.next != null){
            temp = temp.next;
        }
        temp.next = node;
    }
    // 显示链表： 遍历
    public void show(){
        // 首先是判断链表是否是为空
        if (isEmpty()){
            System.out.println("链表为空没有数据");
        }else{
            // 创建一个辅助指针
            Node temp = dummyHead.next;
            //只要当前的节点不为空 就显示节点中的数据
            while( temp != null ){
                System.out.println(temp.name+":"+temp.nickname+":"+temp.no);
                temp =temp.next;
            }

        }
    }

    public void show(Node node){
        // 首先是判断链表是否是为空
        if (node == null){
            System.out.println("链表为空没有数据");
        }else{
            // 创建一个辅助指针
            Node temp = node.next;
            //只要当前的节点不为空 就显示节点中的数据
            while( temp != null ){
                System.out.println(temp.name+":"+temp.nickname+":"+temp.no);
                temp =temp.next;
            }

        }
    }

    private boolean isEmpty() {
        return dummyHead.next == null;
    }
    //  使用的是插入的方式 按照的是节点的node.no属性

    public void insert(Node node){
        // 如果list为空 直接插入
        if (isEmpty()){
            add(node);

        }else {
            // 如果不为空 进行遍历
            Node temp = dummyHead;
            while (temp.next != null) {
                if (node.no == temp.next.no) {
                    //
                    System.out.println("节点的已经存在");
                    break;
                }
                if (node.no < temp.next.no) {// 只需要找到第一个比你序号大的节点
                    node.next = temp.next;
                    temp.next = node;
                    break;
                }

                temp = temp.next;
            }
            //  从循环中退出来表示的是已经到单向链表的末尾了
            temp.next = node;
        }

    }
    // 修改节点的名字：通过节点的编号
    public void update(Node node){
         if (isEmpty()){
             add(node);
         }
         // 设置一个flag
        boolean flag = false;
         Node temp = dummyHead;
         while(temp != null){
             if (temp.no == node.no){
                 temp.name = node.name;
                 flag = true;
                 // 知道找到的修改的节点就退出循环
                 break;
             }
             temp = temp.next;
         }
         if (!flag){
             System.out.println("没有这个节点");
         }
    }
    // 删除节点通过节点的编号
    public void delete(int no){
        if (isEmpty()){
            System.out.println("链表为空不能删除节点");
        }
        boolean flag = true;
        Node temp = dummyHead;
        while(temp.next != null){
            if (temp.next.no == no){
                temp.next = temp.next.next;
                flag = false;
                // 找到了就直接退出循环
                break;
            }
            temp = temp.next;
        }
        if (flag){
            System.out.println("没有这样的节点");
        }
    }
    // 获取单链表的节点的个数 不统计头节点
    public int NodeNum(){
        int nodeNum = 0;
        // 创建辅助指针
        Node temp = dummyHead.next;
        while(temp != null){
            temp = temp.next;
            nodeNum ++;
        }
        return nodeNum;
    }
    public int nodeNum(Node node, int i){
        if (node.next != null){
          return  nodeNum(node.next, i+1);
        }
        return i;
    }
    // 将单链表进行翻转
    // 思路就是头插法： 首先创建一个新的节点： 2 从原来的的链表中取出节点  插入到新节点的后面
    // 最后将dummyHead.next = newHead.next;
    public Node reverse(){
        Node newHead = new Node(0,"","");
        // 创建辅助指针 进行单链表的遍历
        Node temp = dummyHead.next;
        Node next = null;// 保存的 是后一个节点的位置
        while(temp != null){
            // 将新的节点取出来
            next = temp.next;// 先将链表的后一个节点进行保留
            temp.next = newHead.next;
            newHead.next = temp;// 这两步是将节点进行插入
            temp =next;// 节点向后移动
        }
        dummyHead.next = newHead.next;
        return dummyHead;
    }
    // 将单向链表进行倒序输出
    public  void reversePrint(Node node){
        /*if (node.next != null){
            System.out.println(node.no);
            return reversePrint(node.next);
        }
        System.out.println(node.no);
        return node;*/
        // 使用递归的形式来逆向的输出节点
        if (node.next != null) {
            reversePrint(node.next);
        }
        System.out.println(node);
       /* System.out.println(node.no);
        node.next != null? reversePrint(node.next): node;*/
    }
    public void reversePrint(){
        reversePrint(dummyHead.next);
    }



    public static void main(String[] args) {
        SingleLinkedList list = new SingleLinkedList();
        Node node1 = new Node(1,"zhangsan", "zhangxiaosan");
        Node node2 = new Node(2,"lisi", "zhangxiaosan");
        Node node3 = new Node(3,"wangwu", "zhangxiaosan");
        Node node4 = new Node(4,"zhaoliu ", "zhangxiaosan");
        Node node5 = new Node(5,"zhaoliu ", "zhangxiaosan");
        Node node6 = new Node(6,"zhaoliu ", "zhangxiaosan");
        Node node7 = new Node(7,"zhaoliu ", "zhangxiaosan");
       list.insert(node1);
       list.insert(node3);
       list.insert(node2);
       list.insert(node5);
       list.insert(node6);
       list.insert(node7);
       list.insert(node4);
        int i = list.nodeNum(node1,1);
        System.out.println(i);

     /*  list.update(node4);
      //ist.update(new Node(1,"123","123"));
        int i = list.NodeNum();
        System.out.println(i);
        list.delete(5);*/
        /*list.show();
        list.reverse();
        System.out.println("-----------");*/
       /* list.show();

        list.reversePrint(node1);*/
    }
}

