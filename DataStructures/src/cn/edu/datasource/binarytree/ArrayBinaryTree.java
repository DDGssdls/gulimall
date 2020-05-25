package cn.edu.datasource.binarytree;



/**
 * @Author: DDG
 * @Date: 2020/5/5 16:23
 * @Description: 顺序二叉树 一般上就只考虑是完全二叉树  第n个节点的左子节点 为2*n + 1 第n个节点的右子节点 为2*n + 2
 * 完全二叉树： 就是 假如是k层 只有最后的一层是 不满的 其余都是满的 且 不满的那一层 都是 靠左的
 */
public class ArrayBinaryTree {
    //顺序存储二叉树的实现
    private int[] arr;


    public ArrayBinaryTree(int[] arr) {
        this.arr = arr;
    }

    // 进行前序遍历： 需要传入就是数组的下标
    public void previousOrder(int index){
        if(arr == null || arr.length == 0){
            System.out.println(" 数组为空 不能遍历");
            return;
        }
        System.out.print(arr[index] + "\t");
        if (index * 2 + 1 < arr.length){
            previousOrder(index * 2 + 1);
        }
        if (index * 2 + 2 < arr.length){
            previousOrder(index * 2 + 2);
        }
    }
    // 进行中序遍历： 需要传入就是数组的下标
    public void middleOrder(int index){
        if(arr == null || arr.length == 0){
            System.out.println(" 数组为空 不能遍历");
            return;
        }
        if (index * 2 + 1 < arr.length){
            middleOrder(index * 2 + 1);
        }
        System.out.print(arr[index] + "\t");
        if (index * 2 + 2 < arr.length){
            middleOrder(index * 2 + 2);
        }
    }
    // 进行前序遍历： 需要传入就是数组的下标
    public void nextOrder(int index){
        if(arr == null || arr.length == 0){
            System.out.println(" 数组为空 不能遍历");
            return;
        }

        if (index * 2 + 1 < arr.length){
            nextOrder(index * 2 + 1);
        }
        if (index * 2 + 2 < arr.length){
            nextOrder(index * 2 + 2);
        }
        System.out.print(arr[index] + "\t");
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7};
        ArrayBinaryTree arrayBinaryTree = new ArrayBinaryTree(arr);
        arrayBinaryTree.previousOrder(0);
        System.out.println();
        arrayBinaryTree.middleOrder(0);
        System.out.println();
        arrayBinaryTree.nextOrder(0);
    }
}
