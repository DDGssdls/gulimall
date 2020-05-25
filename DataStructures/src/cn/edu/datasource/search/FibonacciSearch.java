package cn.edu.datasource.search;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @Author: DDG
 * @Date: 2020/5/3 17:27
 * @Description:
 */
public class FibonacciSearch  {

    private static final int MAX_SIZE = 20;

    public static void main(String[] args) {
        int[] arr = {1, 8, 10, 10, 89, 1000,  1234};
        int[] arr1 = {1, 3, 5, 6, 7, 7,  9};
        //int i = insertSearch(arr, 8);
       /* int[] fib = fib();
        System.out.println(Arrays.toString(fib));
        int[] fib1 = new int[MAX_SIZE];
        fib(fib1, 19);
        System.out.println(Arrays.toString(fib1));*/
        int i = fibSearch(arr1, 9);
        System.out.println(i);
    }
    // 进行fib的构建
    public static int[] fib(){
        int[] fibArr = new int[MAX_SIZE];
        fibArr[0] = 1;
        fibArr[1] = 1;
        for (int i = 2; i < MAX_SIZE; i++) {
            fibArr[i] = fibArr[i - 1] + fibArr[i - 2];
        }
        return fibArr;
    }
    // 使用的是带缓存的递归
    public static int fib(int[] arr, int i){
        if(i == 1){
            arr[i] = 1;
            return arr[i];
        }
        if(i == 0){
            arr[i] = 1;
            return arr[i];
        }
        arr[i] = fib(arr, i -1) + fib(arr, i - 2);
        return arr[i];
    }
    // 使用的是fib查找
    public static int fibSearch(int[] arr, int value){
        int low = 0;
        int high = arr.length - 1;
        int k = 0;// 表示的是 fib 的下标
        int mid = 0;
        int[] fib = fib();
        //获取fib分割的下标 首先就是将 数组中长度提升到 fib[k] 个 索引就是 0 - fib[k] -1
        while (high > fib[k] - 1){
            k ++;
        }
        // 将原数组进行拷贝 就是拷贝大temp中进行比较 实际的比较的操作都是在 temp中进行的
        // temp 中 超过high 的 下标中的元素都是零
        int[] temp = Arrays.copyOf(arr, fib[k]);
        // 这里就是将   temp 中的零进行填充 使用的是最后的一个数值
        // 使用最大值进行high 的填充
        for (int i = high + 1; i < temp.length; i++) {
            temp[i] = arr[high];
        }
        // 进行查找：
        while (low <= high){
            // 进行mid 的获取 使用的是 fib[k] = fib[k - 2] + fib[k - 1]
            // 如果 小于 mid 的获取  1  1  2  3  5  8
            mid = low + fib[k - 1] - 1;
            if (value < temp[mid]){
                high = mid - 1;
                k --;
            }
            if (value > temp[mid]){
                low = mid + 1;
                k -= 2;
            }
            if (mid <= high){
                return mid;
            }else {
                return high;
            }

        }
        return -1;
    }
}
