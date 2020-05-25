package cn.edu.datasource.binarytree;

import cn.edu.datasource.hash.Employee;

import java.util.Objects;

/**
 * @Author: DDG
 * @Date: 2020/5/4 16:29
 * @Description:
 */
public class BinarySearchTree {



    public static void main(String[] args) {
        //  二叉树的前序遍历 就是： 根 左右 中序： 左 根 右 后序： 左 右 根 父节点确定 遍历

       TreeNode treeNode1 = new TreeNode(1);
       TreeNode treeNode2 = new TreeNode(2);
       TreeNode treeNode3 = new TreeNode(3);
       TreeNode treeNode4 = new TreeNode(4);
       TreeNode treeNode5 = new TreeNode(5);
       TreeNode treeNode6 = new TreeNode(6);
       TreeNode treeNode7 = new TreeNode(7);
        treeNode1.setLeftNode(treeNode2);
        treeNode1.setRightNode(treeNode3);
        treeNode2.setLeftNode(treeNode4);
        treeNode2.setRightNode(treeNode5);
        treeNode3.setLeftNode(treeNode6);
        treeNode3.setRightNode(treeNode7);
        previous(treeNode1);
        System.out.println("-------------");
        middle(treeNode1);
        System.out.println("-------------");
        next(treeNode1);
        System.out.println("--------------------");
        System.out.println(leftLevel >= rightLevel ? leftLevel : rightLevel);
        System.out.println(previousSearch(treeNode1, 7));
        System.out.println(middleSearch(treeNode1, 7));
        System.out.println(nextSearch(treeNode1, 7));
        System.out.println("-------------------------------");
        deleteNode(treeNode1, treeNode4);
        previous(treeNode1);



    }
    static int leftLevel = 0;
    static int rightLevel = 0;
    public static void previous(TreeNode treeNode){
        System.out.print(treeNode.num );
        if (treeNode.leftNode != null){
            leftLevel ++;
            previous(treeNode.leftNode);
        }
        if (treeNode.rightNode != null){
            rightLevel ++;
            previous(treeNode.rightNode);
        }
    }
    public static void middle(TreeNode treeNode){

        if (treeNode.leftNode != null){
            middle(treeNode.leftNode);
        }
        System.out.print(treeNode.num);
        if (treeNode.rightNode != null){
            middle(treeNode.rightNode);
        }
    }
    public static void next(TreeNode treeNode){

        if (treeNode.leftNode != null){
            next(treeNode.leftNode);
        }

        if (treeNode.rightNode != null){
            next(treeNode.rightNode);
        }
        System.out.print(treeNode.num);
    }
    public static TreeNode previousSearch(TreeNode treeNode, int  no){
        if (no == treeNode.num) {
            return treeNode;
        }
        TreeNode resultNode = null;
        if (treeNode.leftNode != null){
            resultNode = previousSearch(treeNode.leftNode, no);
        }
        if (resultNode != null){
            return resultNode;
        }

        if (treeNode.rightNode != null){
            resultNode =  previousSearch(treeNode.rightNode, no);
        }
        return resultNode;
    }

    public static TreeNode middleSearch(TreeNode treeNode, int  no){

        TreeNode resultNode = null;
        if (treeNode.leftNode != null){
            resultNode = middleSearch(treeNode.leftNode, no);
        }
        if (resultNode != null){
            return resultNode;
        }
        if (no == treeNode.num) {
            return treeNode;
        }

        if (treeNode.rightNode != null){
            resultNode =  middleSearch(treeNode.rightNode, no);
        }
        return resultNode;
    }
    public static TreeNode nextSearch(TreeNode treeNode, int  no){
        TreeNode resultNode = null;
        if (treeNode.leftNode != null){
            resultNode = nextSearch(treeNode.leftNode, no);
        }
        if (resultNode != null){
            return resultNode;
        }

        if (treeNode.rightNode != null){
            resultNode =  nextSearch(treeNode.rightNode, no);
        }
        if (resultNode != null){
            return resultNode;
        }
        if (no == treeNode.num) {
            return treeNode;
        }
        return null;
    }

    public static void deleteNode(TreeNode rootNode, TreeNode node){
        if (rootNode == null || Objects.equals(rootNode, node)){
            rootNode = null;
            return;
        }
        if (Objects.equals(rootNode.leftNode, node)){
            rootNode.leftNode= null;
            return;
        }
        if (Objects.equals(rootNode.rightNode, node)){
            rootNode.rightNode = null;
            return;
        }

        if (rootNode.leftNode != null){
            deleteNode(rootNode.leftNode, node);
        }
        if (rootNode.rightNode != null){
            deleteNode(rootNode.rightNode, node);
        }
    }


    static class TreeNode{
        private int num;

        private TreeNode leftNode;

        private TreeNode rightNode;

        public TreeNode(int num) {
            this.num = num;
        }

        public TreeNode(int num, TreeNode leftNode, TreeNode rightNode) {
            this.num = num;
            this.leftNode = leftNode;
            this.rightNode = rightNode;
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
