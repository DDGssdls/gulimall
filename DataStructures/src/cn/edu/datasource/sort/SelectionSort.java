package cn.edu.datasource.sort;

import java.util.Arrays;

public class SelectionSort {
    // p 57
    // 选择排序 思路就像是冒泡排序 一样但是
    // 不是每次进行交换 而是找到最小的值之后才进行交换
    // 时间复杂度和 bubble sort的时间复杂度相同 都是o n^2
    public static void main(String[] args) {
        int[] arr = new int[80000];
        for (int i = 0; i < 800000; i++) {
            arr[i] = (int) (Math.random()*8000000);
        }
        long begin = System.currentTimeMillis();
        selectionSort(arr);
        long end = System.currentTimeMillis();
        System.out.println(end - begin);
    }
    public static void selectionSort(int[] nums){
        int index = 0; // 存储下标
        int num = Integer.MAX_VALUE;
        int temp = 0;// 进行交换使用的中间变量
        for (int i = 0; i < nums.length -1 ; i++) {// 需要交换的轮次是n -1 次
            for (int j = i; j < nums.length ; j++) {
                if (nums[j] < num){
                    num = nums[j];
                    index = j;
                }
            }
            // 进过循环这样就找到了最小的值 进行交换就是 nums[i] 和 nums[index] 但是和bubble sort不同的是
            // 它是一定会进行交换的 能进行判断 就是 index = i 就不进行交换
            /*if (index != i){ 这是优化的部分 只有 不在正确的位置上才进行交换
                temp = nums[index];
                nums[index] = nums[i];
                nums[i] = temp;
            }*/
            temp = nums[index];
            nums[index] = nums[i];
            nums[i] = temp;

            // 将交换完成的变量进行重置 就像是bubble sort 中的flag 一样
            index = 0; // 存储下标
            num = Integer.MAX_VALUE;
            //System.out.println(Arrays.toString(nums));
        }
        //System.out.println(Arrays.toString(nums));
    }

    public static void selectionSort2(int[] nums){// 这样的形式是找到最大的一个值
        int index = 0; // 存储下标
        int num = Integer.MIN_VALUE;
        int temp = 0;// 进行交换使用的中间变量
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = 0; j < nums.length - i; j++) {
                if (nums[j] > num){
                    num = nums[j];
                    index = j;
                }
            }
            // 进过循环这样就找到了最大的值 进行交换就是 nums[i] 和 nums[index] 但是和bubble sort不同的是
            // 它是一定会进行交换的 能进行判断 就是 index = i 就不进行交换
            /*if (index != i){ 这是优化的部分 只有 不在正确的位置上才进行交换
                temp = nums[index];
                nums[index] = nums[i];
                nums[i] = temp;
            }*/
            // 这里是将找到的最大的值 和 数组中的最后的值进行交换 最后的一个数组中的索引就是 nums.length - 1- i
            temp = nums[index];
            nums[index] = nums[nums.length - i- 1];
            nums[nums.length - i -1] = temp;

            // 将交换完成的变量进行重置 就像是bubble sort 中的flag 一样
            index = 0; // 存储下标
            num = Integer.MIN_VALUE;
            System.out.println(Arrays.toString(nums));
        }

    }
}


