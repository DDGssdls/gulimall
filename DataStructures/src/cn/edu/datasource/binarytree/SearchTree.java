package cn.edu.datasource.binarytree;


/**
 * @Author: DDG
 * @Date: 2020/5/8 16:50
 * @Description:
 */
public class SearchTree {
    public static void main(String[] args) {
        int[] arr = {7, 3, 10, 12, 5, 1, 1, 9};
        Node node = create(arr);
        middleOrder(node);
        System.out.println();
        deleteNode(10, node);
        middleOrder(root);


    }

    // 中序遍历：
    public static void middleOrder(Node node) {
        if (node == null) {
            System.out.println("buxing ");
            return;
        }
        if (node.left != null) {
            middleOrder(node.left);
        }
        System.out.print(node.data + "\t");
        if (node.right != null) {
            middleOrder(node.right);
        }
    }

    private static Node root;

    public static Node create(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        root = new Node(arr[0]);
        for (int i = 1; i < arr.length; i++) {
            createSearchTree(arr[i], root);
        }

        return root;
    }

    // 进行节点的删除：节点三种情况 1 就是节点就是叶子节点 直接删除 2 要是 节点有一个子树 3 有两个子树
    // 需要注意的就是根节点：
    public static void deleteNode(int num, Node node) {

        if (node == null ){
            return;
        }

        if (node.left != null) {
            if (node.left.data == num) {
                if (node.left.left != null && node.left.right == null){
                    node.left = node.left.left;
                }else if(node.left.left != null && node.left.right != null){
                    node.left = node.left.right;
                }else if (node.left.left == null && node.left.right != null){
                    node.left = node.left.right;
                }else{
                    node.left = null;
                }

            } else {
                deleteNode(num, node.left);
            }
        }
        if (node.right != null) {
            if (node.right.data == num) {
                if (node.right.left != null && node.right.right == null){
                    node.right = node.right.left;
                }else if(node.right.left != null && node.right.right != null){
                    node.right = node.right.right;
                }else if (node.right.left == null && node.right.right != null){
                    node.right = node.right.right;
                }else{
                    node.right = null;
                }

            } else {
                deleteNode(num, node.right);
            }
        }
    }


    // 构建二叉搜索树
    public static void createSearchTree(int arr, Node node) {
        if (node == null) {
            return;
        }
        if (arr < node.data) {
            if (node.left != null) {
                createSearchTree(arr, node.left);
            } else {
                node.left = new Node(arr);
                return;
            }
        } else {
            if (node.right != null) {
                createSearchTree(arr, node.right);
            } else {
                node.right = new Node(arr);
                return;
            }
        }


    }

    private static class Node {
        private int data;
        private Node left;
        private Node right;

        public Node() {
        }

        public Node(int data) {
            this.data = data;
        }

        public Node(int data, Node left, Node right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }

        public int getData() {
            return data;
        }

        public void setData(int data) {
            this.data = data;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "data=" + data +
                    '}';
        }
    }
}
