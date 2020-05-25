package cn.edu.datasource.sort;

import java.util.Arrays;

/**
 * @Author: DDG
 * @Date: 2020/4/25 16:07
 * @Description:
 */
public class RadixSort {
    public static void main(String[] args) {
        int[] arr = {53, 3, 542, 748, 14, 214};
        RadixSort(arr);

    }
    public static void RadixSort(int[] arr){
        int[][] bucket = new int[10][arr.length];
        //定义一个一维数组放置桶中的个数
        int[] num = new int[10];
        int temp;
        int maxNum = arr[0];
        for (int i : arr) {
            if (i > maxNum)
                maxNum = i;
        }
        // 求出最大的位数
        int length = String.valueOf(maxNum).length();
        for (int multi = 1, n = 1; multi <=  length; multi ++, n *= 10) {
            for (int i : arr) {
                temp = i/ n;
                temp %= 10;
                bucket[temp][num[temp] ++] = i;
            }
            // 从每一个桶中获取数据
            int index = 0;
            for (int i = 0; i < num.length; i++) {
                for (int j = 0; j < num[i]; j++) {
                    arr[index ++] = bucket[i][j];
                }
                num[i] = 0;
            }
        }
        System.out.println(Arrays.toString(arr));



    }
}
