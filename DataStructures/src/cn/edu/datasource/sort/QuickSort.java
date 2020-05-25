package cn.edu.datasource.sort;

import java.util.Arrays;

public class QuickSort {

    public static void main(String[] args) {

        int[] arr = new int[]{1,3, 5, 7,7,7,1,1,1,1};

        long begin = System.currentTimeMillis();
        quickSort2(arr,0, arr.length -1);
        long end = System.currentTimeMillis();
        System.out.println(end - begin);
        System.out.println(Arrays.toString(arr));
    }

    public static void quickSort(int[] arr, int left, int right, int base){
        int l = left;
        int r = right;
        int b = base;
        int temp = 0;

        // 这样的目的就是为了使 比middle小的方在左边 大的方在右边
        while (l < r){
            while (l < right && arr[l] < arr[b] ){
                l ++;
            }
            while (r > left && arr[r] > arr[b]  ){
                r --;
            }
            if (l < r){
                temp = arr[l];
                arr[l] = arr[r];
                arr[r] = temp;
            }

            if (l >= r){
                temp = arr[b];
                arr[b] = arr[l];
                arr[l] = temp;
                break;
            }



            // 要是都找到了 就是进行交换

        }
        // 要是没有找到就是进行交换

        // 进行左右的递归：
        // 左递归
        if (l - left > 2){

            quickSort(arr, left, l - 2, l -1);
        }
//        if (right - l>= 1){
//
//            quickSort(arr, l + 1 , right, base);
//        }
    }

    public static void quickSort2(int[] arr, int left, int right){
        int l = left;
        int r= right;
        int base = arr[(left + right) / 2];
        int  temp = 0;
        while (l < r){
            while (arr[l] < base){
                l ++;
            }
            while (arr[r] > base){
                r --;
            }
            if (l < r){
                temp = arr[l];
                arr[l] = arr[r];
                arr[r] = temp;
            }
            if (l > r){
                break;
            }

            // 这样的判断就是防止 base有等值出现等值的时候导致的死循环
            if (arr[l] == base){
                l ++;
            }
            if (arr[r] == base){
                r --;
            }
        }
        
        if(left < r){
            quickSort2(arr, left, r);
        }
        if(l < right){
            quickSort2(arr, l, right);
        }
    }
}
