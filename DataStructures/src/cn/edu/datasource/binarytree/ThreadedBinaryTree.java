package cn.edu.datasource.binarytree;

/**
 * @Author: DDG
 * @Date: 2020/5/5 16:59
 * @Description: 线索化二叉树 ：就是将叶子节点的空数组域进行前驱 和 后继的指向
 */
public class ThreadedBinaryTree {
    public static void main(String[] args) {
        TreeNode treeNode1 = new TreeNode(1);
        TreeNode treeNode3 = new TreeNode(3);
        TreeNode treeNode6 = new TreeNode(6);
        TreeNode treeNode8 = new TreeNode(8);
        TreeNode treeNode10 = new TreeNode(10);
        TreeNode treeNode14 = new TreeNode(14);

        treeNode1.setLeftNode(treeNode3);
        treeNode1.setRightNode(treeNode6);
        treeNode3.setLeftNode(treeNode8);
        treeNode3.setRightNode(treeNode10);
        treeNode6.setLeftNode(treeNode14);

        threadedNodes(treeNode1);
        System.out.println(treeNode10.getLeftNode());
        System.out.println(treeNode10.getLeftType());
        System.out.println(treeNode10.getRightNode());
        System.out.println(treeNode10.getRightType());
        System.out.println(treeNode8.getRightType());
        threadedMiddleOrder(treeNode1);

    }
    // 将二叉树进行线索化 方法：
    static TreeNode pre = null;

    public static void threadedNodes(TreeNode treeNode){
        if (treeNode == null){
            //System.out.println("为空 不能线索化");
            return;
        }

        // 进行的是中序的判断
        threadedNodes(treeNode.leftNode);
        // 进行当前节点的判断：
        if (treeNode.leftNode == null){
            // 进行左节点的线索化
            treeNode.leftNode = pre;
            // 修改节点的指向的类型
            treeNode.leftType = 1;
        }
        // 进行后继节点的处理 不是当前节点进行判断而是下一次进行判断的
        if (pre != null && pre.rightNode == null){
            pre.rightNode = treeNode;
            pre.rightType = 1;
        }
        // 处理完成之后 需要注意的就是 当前节点是下一个节点的 前驱节点 !!! 不进行处理就是pre永远为空
        pre = treeNode;
        // 将 左 中 进行处理之后进行 右边的处理
        threadedNodes(treeNode.rightNode);
    }
    // 使用线索化之后 原来的方式不能进行中序遍历 而是需要修改原来遍历的方式：
    public static void threadedMiddleOrder(TreeNode treeNode){
        if (treeNode == null){
            System.out.println("节点为空");
        }
        while (treeNode != null) {
            while (treeNode.leftType == 0) {
                treeNode = treeNode.leftNode;
            }
            System.out.println(treeNode);
            while (treeNode.getRightType() == 1) {
                treeNode = treeNode.rightNode;
                System.out.println(treeNode);
            }
            // 替换这个遍历的节点 第一次就是只能指向到 3号节点 这个后的节点不是 后继
            // 而是真实的指向的节点 所以需要将节点替换
            treeNode = treeNode.rightNode;
        }
    }

    public static void threadedPreviousOrder(TreeNode treeNode){
        if (treeNode == null){
            System.out.println("节点为空");
        }
    }
    static class TreeNode{
        private int num;

        private TreeNode leftNode;

        private TreeNode rightNode;

        // 线索化二叉树： 左指向可能是 左边的节点 可能是 前驱节点 同样的 右边的也是
        // 如果是0 表示的是左子树 要是 1 表示的是前驱节点
        private int leftType;

        private int rightType;

        public TreeNode(int num, TreeNode leftNode, TreeNode rightNode) {
            this.num = num;
            this.leftNode = leftNode;
            this.rightNode = rightNode;
        }


        public int getLeftType() {
            return leftType;
        }

        public void setLeftType(int leftType) {
            this.leftType = leftType;
        }

        public int getRightType() {
            return rightType;
        }

        public void setRightType(int rightType) {
            this.rightType = rightType;
        }

        public TreeNode(int num) {
            this.num = num;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public TreeNode getLeftNode() {
            return leftNode;
        }

        public void setLeftNode(TreeNode leftNode) {
            this.leftNode = leftNode;
        }

        public TreeNode getRightNode() {
            return rightNode;
        }

        public void setRightNode(TreeNode rightNode) {
            this.rightNode = rightNode;
        }

        @Override
        public String toString() {
            return "TreeNode{" +
                    "num=" + num +
                    '}';
        }
    }
}
