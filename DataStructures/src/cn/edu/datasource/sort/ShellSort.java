package cn.edu.datasource.sort;

import java.time.temporal.Temporal;
import java.util.Arrays;

public class ShellSort {// 希尔排序就是一种更加高级的插入排序 p64
    // 就是首先通过交换法 或者是移动法进行宏观上的调控 是的相对有序 shell 排序就是一种插入排序 但是也叫是缩小增量排序
    // 不是稳定的

    public static void main(String[] args) {
        int[] arr = {8,9, 1, 7, 2, 3, 5, 4, 6, 0};
//        arr = shellSort(arr);
        int[] arrs = new int[8000000];

        for (int i = 0; i < 8000000; i++) {
            arrs[i] = (int) (Math.random() * 8000000);
        }
         long s = System.currentTimeMillis();
       shellSort7(arr);
        System.out.println(Arrays.toString(arr));

        long e = System.currentTimeMillis();
        System.out.println(e - s);



    }

    public static int[] shellSort(int[] arr){
        int temp = arr.length;// 使用中间变量进行数组长度的保存
        int num = 0;
        // 首先是交换法
       // 就是直到数组的分组的步长为1 的时候停止
       // 这就是步长 将数组进行分组

            temp = temp /2;
            for (int i = 0; i < temp; i++) {
                if (arr[i] > arr[temp + i]) {
                    num = arr[i];
                    arr[i] = arr[temp + i];
                    arr[temp + i] = num;
                }
            }


        System.out.println(Arrays.toString(arr));
        return arr;
    }
    public static void shellSort2(int[] arr){
        // 使用中间变量进行保存
        int foot = arr.length;
        int current = arr[arr.length -1];
        while (foot > 1){
            foot /= 2;
            while (foot >= 0 && arr[foot] > current){
                arr[arr.length - 1] =  arr[foot];
            }
            arr[foot] = current;
        }
        System.out.println(Arrays.toString(arr));
    }

    public static void shellSort3(int[] arr){
        int temp = 0;
        int length = arr.length;
        while (length > 1){
            length /= 2;
            for (int i = length; i < arr.length; i++) {
                for (int j = i - length; j >=0 ; j -= length) {
                    if (arr[j] > arr[i]){
                        temp = arr[j];
                        arr[j] = arr[i];
                        arr[i] = temp;
                    }
                }
            }
            System.out.println(Arrays.toString(arr));
        }

    }
    public static void shellSort4(int[] arr){
        int temp = 0;
        for (int gap = arr.length / 2;  gap > 0; gap /= 2) {
            for (int i = gap; i < arr.length; i++) {
                for (int j = i - gap; j >=0 ; j -= gap) {
                    if (arr[j] > arr[j + gap]){
                        temp = arr[j];
                        arr[j] = arr[j + gap];
                        arr[j + gap] = temp;
                    }
                }
            }
            //System.out.println(Arrays.toString(arr));
        }
    }

    public static void shellSort5(int[] arr){


        for (int i = arr.length / 2; i > 0 ; i /= 2 ) {
            // 对进行分组之后的进行插入排序
            for (int j = i; j < arr.length ; j++) {
                int k = j;
                int current = arr[k];
                if (arr[k] < arr[k - i]){
                    while (k - i >= 0 && current < arr[k - i]){
                        arr[k] = arr[k - i];
                        k -= i;
                    }
                    arr[k] = current;
                }

            }
        }
        //System.out.println(Arrays.toString(arr));

    }

    public static void shellSort6(int[] arr){
        // 首先就是进行分组 就是缩小增量法
        int temp = arr.length;
        int current = 0;
        int j = 0;
        while (temp > 0){
            temp /= 2;
            // 分组之后使用插入排序 当步长就是一的时候就是插入排序  就是先分组 在插排
            for (int i = temp; i < arr.length; i++) {
                current = arr[i];
                j = i;
                if (arr[j - temp] > arr[i]){
                    while (j - temp >= 0 && arr[j - temp] > current){
                        arr[j] = arr[j - temp];
                        j -= temp;
                    }
                    arr[j] = current;
                }

            }
        }
    }
    public static void shellSort7(int[] arr){
        int current = 0;
        int num = 0;
        for (int i = arr.length / 2; i > 0 ; i /= 2) {
            for (int j = i; j < arr.length; j++) {
                current = arr[j];
                num = j;
                while (num - i >=0 && arr[num - i] > current){
                    arr[num] = arr[num - i];
                    num -= i;
                }
                arr[num] = current;
            }
        }
    }

}
