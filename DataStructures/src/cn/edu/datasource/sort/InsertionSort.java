package cn.edu.datasource.sort;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class InsertionSort {// 插入排序的  基本思路：就是将n个带排序的元素看成两个表 一个有序表 一个无序表
    // 开始的有序表中只有一个元素 排序的过程中依次取出无需表中的数据 插入到有序表中最终形成一个有序表
    // 每次进行排序就是先将第一个元素当做是有序表 剩下的袁旭当做是无需表进行排序

    private static int num = 8000000;


    public static void main(String[] args) {
        int[] arr = new int[num];
        Random random = new Random();
        for (int i = 0; i < num; i++) {
            arr[i] = (int) (Math.random() * 800000);
        }
        long begin = System.currentTimeMillis();
        insertSort(arr);
        long end = System.currentTimeMillis();
        System.out.println(end - begin);

    }

    public static  void insertionSort(int[] arr){
        //int index = 1;//保存着无序表中的第一个下标 这里其实就是i + 1
        int temp = 0;// 中间变量进行保存第一个需要进行排序的位置的元素
        for (int i = 0; i < arr.length - 1; i++) {
            // 无序表中的第一个元素 就是index + 1
            for (int j = 0; j < i + 1 ; j++) {// 就是从有序表中找到需要插入的位置整个流程就是这样的
                if(arr[j] > arr[i + 1]){
                    // 进行位置上的排序
                    temp = arr[i + 1];// 使用中间变量先将元素进行保存
                    for (int k = i; k >= j ; k--) {
                        arr[k + 1] = arr[k];
                    }
                    // 将后面的值进行向后移动一位之后 将arr[j] 进行赋值
                    arr[j] = temp;
                    break;
                }
            }

        }
        System.out.println(Arrays.toString(arr));
    }

    public static void insertSort2(int[] arr){

        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j <= i; j++) {
                if (arr[j] > arr[i]){
                    for (int k = i; k > j; k--) {
                        arr[k] = arr[k - 1];
                    }
                    arr[j] = arr[i];
                    continue;
                }
            }
        }
        System.out.println(Arrays.toString(arr));

    }
    public static void insertSort3(int[] arr){
        int length = arr.length;
        int index = 0;
        int current = 0;

        for (int i = 1; i < length; i++) {
            index = i -1;
            current = arr[i];
            while (index >= 0 && arr[index] > current){
                arr[index + 1] = arr[index];
                index --;
            }
            arr[index + 1] = current;

        }
        System.out.println(Arrays.toString(arr));
    }
    public static void insertSort4(int[] arr){
        int current = 0;
        for (int i = 1; i < arr.length; i++) {
            current = arr[i];
            for (int j = 0; j < i; j++) {
                if (arr[j] > current){
                    for (int k = i; k > j ; k--) {
                        arr[k] = arr[k - 1];
                    }
                    arr[j] = current;

                }
            }
        }
        System.out.println(Arrays.toString(arr));
    }

    public static void insertSort5(int[] arr){
        int current = 0;// 使用中间变量进行保存第一个不是有序的值

        for (int i = 1; i < arr.length; i++) {
            current = arr[i];// 变量保存 中间值

            while (i -1 >= 0 && arr[i - 1] > current){// 这里判断 是前面的一个值是不是比当前的值大
                // 要是大 就是将前面的一个值 向后移动一位  两种条件跳出循环一种就是前面没有了表示的就是到第一个了
                // 另一种跳出循环的方式就是 找到了 就是将这个值插入 就行 了
                arr[i] = arr[i - 1];
                i --;
            }
            arr[i] = current;
        }
        System.out.println(Arrays.toString(arr));
    }

    public static void insertSort6(int[] arr){
        int current = 0;
        for (int i = 1; i < arr.length; i++) {
            current = arr[i];
            while (i -1 >= 0 && arr[i - 1] > current){
                arr[i] = arr[i - 1];
                i --;
            }
            arr[i] = current;

        }
        System.out.println(Arrays.toString(arr));
    }
    public static void insertSort7(int[] arr){
        int current = 0;
        for (int i = 1; i < arr.length; i++) {
            current = arr[i];
            while (i - 1 >= 0 && arr[i - 1] > current){
                arr[i] = arr[i - 1];
                i --;
            }
            arr[i] = current;
        }
        System.out.println(Arrays.toString(arr));
    }

    public static void insertSort(int[] arr){
        int current ;
        for (int i = 1; i < arr.length; i++) {
            current = arr[i];
            while (i -1 >= 0 && arr[i - 1] > current){
                arr[i - 1] = arr[i];
                i --;
            }
            arr[i] = current;
        }
        //System.out.println(Arrays.toString(arr));
    }
}
