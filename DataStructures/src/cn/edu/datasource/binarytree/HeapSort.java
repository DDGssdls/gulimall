package cn.edu.datasource.binarytree;

import java.util.Arrays;
import java.util.Random;

/**
 * @Author: DDG
 * @Date: 2020/5/6 14:12
 * @Description: 实现堆排序 要是使用升序排序就是使用大顶堆 要是使用降序就是使用小顶堆 节点的左子节点就是 2K+1
 * 右子节点 就是 2K + 2  飞叶子节点 就是 k /2 - 1
 */
public class HeapSort {
    public static void main(String[] args) {
        //int[] arr = {4, 6, 8, 5, 9 };
        int[] arr = new int[8000000];
        Random random = new Random();
        for (int i = 0; i < 8000000; i++) {
            arr[i] = 0;
        }
        long l = System.nanoTime();
        heapSort(arr);
        long l1 = System.nanoTime();
        System.out.println((l1 - l)/ 100000000);
    }

    public static void heapSort(int[] arr){
        /*adjustHeap(arr, 1, 5);
        adjustHeap(arr, 0, 5);*/
        // 进行大顶对的构建
        int temp = 0;
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            adjustHeap(arr, i, arr.length);
        }
        // 进行交换
        for (int j = arr.length -1; j >= 0; j --){
            temp = arr[j];
            arr[j] = arr[0];
            arr[0] = temp;
            // 在进行构建
            adjustHeap(arr, 0, j);
        }
    }

    // 就是将数组转成最大堆：arr 表示的是要调整的数组 i表示的是非叶子节点的索引 length表示的是数组的长度
    private static void adjustHeap(int[] arr, int i, int length){
        // 使用中间变量 将arr[i] 进行保存 用于交换使用
        int temp = arr[i];
        // 循环判断右子树
        for (int j = i * 2 + 1; j < length; j = j * 2 + 1) {
            // 要是左子树的值 小于 右子树 的值 就是重新指向
            if( j  + 1 < length && arr[j] < arr[j + 1]){
                j ++;
            }
            if (arr[j] > temp){
                // 进行交换
                arr[i] = arr[j];
                i = j;
             }else{
                // ?
                break;
            }
        }
        // 循环完成之后进行交换
        arr[i] = temp;


    }
}
