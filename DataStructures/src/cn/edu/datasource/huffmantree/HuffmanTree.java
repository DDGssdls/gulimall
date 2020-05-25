package cn.edu.datasource.huffmantree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: DDG
 * @Date: 2020/5/6 15:25
 * @Description: 霍夫曼树 最短路径长度 实现方式 就是 进行排序后 最小的权的树就是一个树的两个子树
 * 然后将生成的这个最小的树 在进行比较 最终是只有一个节点 节点的权 和 节点的带权路径长度
 *  所以 所有的需要进行赫夫曼树构建的节点都是在叶子节点上
 *   赫夫曼编码的实现就是将字符出现的次数作为权值进行赫夫曼树的创建 然后根据路径进行编码 左边的是0 右边的是1
 *   这样的方式 编码方式最有优 （路径最短 ） 且没有二义性 每一个路径都是不同的
 *    w p l width path length
 */
public class HuffmanTree {
    private static final int LENGTH = 10;
    public static void main(String[] args) {
        /*ArrayList<Node> nodes = new ArrayList<>();
        for (int i = 20; i > 0; i--) {
            nodes.add(new Node(i, null, null));
        }
        List<Node> collect = nodes.stream().sorted((n1, n2) -> n1.value - n2.value).limit(2).collect(Collectors.toList());
        System.out.println(collect.toString());
       int[] arr = {4, 3, 2, 1};
       // 需要注意的就是 不能直接使用 Arrays.asList 基本类型进行使用 ArrayList中都是包装类型
        List<Node> collect = Arrays.stream(arr).boxed().map(integer -> new Node(integer)).sorted((n1, n2) ->{
            return  n1.value - n2.value;
        }).limit(2).collect(Collectors.toList());
        for (Node node : collect) {
            System.out.println(node);
        }*/
//        int[] arr = {13, 7, 8, 3, 29, 6, 1};
        int[] arr = {1, 1, 1, 2, 2, 2, 4, 4, 4, 5, 5, 9};
        Node node = createHfmTree(arr);
        System.out.println(node.getValue());
        previousOrder(node);
        System.out.println();
      /*  String java = "i like like like jva do you like a java";*/


    }
    public static Node createHfmTree(int[] arr){
        //第一步为了操作方便 遍历arr  构建node 放到ArrayList中
        List<Node> collect = Arrays.stream(arr).boxed().map(Node::new)
                .sorted(Comparator.comparingInt(n -> n.value)).collect(Collectors.toList());

        while(collect.size() > 1){
            Node node0 = collect.get(0);
            Node node1 = collect.get(1);
            Node parent = new Node(node0.value + node1.value, node0, node1);
            collect.remove(0);
            // 这里要是按照index 需要删除两次index 0 不是删除 index 0 和 1 进行删除会出现越界
            collect.remove(0);
            /*collect.remove(node0);
            collect.remove(node1);*/

            collect.add(parent);
            collect.sort((n1, n2) -> n1.value -n2.value);
        }
        return collect.get(0);

    }
    public static void previousOrder(Node node){
        if (node == null){
            System.out.println("不行");
            return;
        }
        System.out.print(node.value + "\t");
        if (node.leftNode != null){
            previousOrder(node.leftNode);
        }
        if (node.rightNode != null){
            previousOrder(node.rightNode);
        }
    }

    static class Node{
        int value;
        Node leftNode;
        Node rightNode;

        public Node(int value, Node leftNode, Node rightNode) {
            this.value = value;
            this.leftNode = leftNode;
            this.rightNode = rightNode;
        }

        public Node(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public Node getLeftNode() {
            return leftNode;
        }

        public void setLeftNode(Node leftNode) {
            this.leftNode = leftNode;
        }

        public Node getRightNode() {
            return rightNode;
        }

        public void setRightNode(Node rightNode) {
            this.rightNode = rightNode;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    '}';
        }
    }
}
