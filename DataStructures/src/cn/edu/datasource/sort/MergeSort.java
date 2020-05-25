package cn.edu.datasource.sort;

import java.util.Arrays;

/**
 * @Author: DDG
 * @Date: 2020/4/25 13:09
 * @Description: 归并排序算法：
 */
public class MergeSort {

    public static void main(String[] args) {
        //int[] arr = {8, 4, 5, 7 ,1, 3, 6, 2};
        int[] arr = {4, 5, 7, 8, 1, 2, 3, 6};
        int[] temp = new int[arr.length];

         mergeSort(arr, 0, arr.length - 1 , temp);
        System.out.println(Arrays.toString(arr));


    }
    public static void merge(int[] arr, int left, int mid, int right, int[] temp){
        int l = left;
        int m = mid + 1;
        int index = left;
        while (l <= mid  && m <= right){
            if (arr[l] <= arr[m]){
                temp[index++] = arr[l++];
            }else{
                temp[index++] = arr[m++];
            }
        }
        while (l <= mid){
            temp[index++] = arr[l++];
        }
        while (m <= right){
            temp[index++] = arr[m++];
        }
        // 将临时的数组进行拷贝的原数组中
        //System.out.println(Arrays.toString(temp));
        for (int k = left; k <= right ; k++) {
            arr[k] = temp[k];
        }

    }

    public static void mergeSort(int[] arr, int left, int right , int[] temp){
        if (left < right){
            int mid = (left + right) /2;
            // 进行左右递归：
            mergeSort(arr, left, mid, temp);
            mergeSort(arr, mid + 1, right, temp);
            // 进行合并：
            merge(arr, left, mid, right, temp);
        }

    }
}



 class MergeSort2 {
    //两路归并算法，两个排好序的子序列合并为一个子序列
    public static void merge(int []a,int left,int mid,int right ,int[] tmp){
        int p1=left,p2=mid+1,k=left;//p1、p2是检测指针，k是存放指针

        while(p1<=mid && p2<=right){
            if(a[p1]<=a[p2])
                tmp[k++]=a[p1++];
            else
                tmp[k++]=a[p2++];
        }

        while(p1<=mid) tmp[k++]=a[p1++];//如果第一个序列未检测完，直接将后面所有元素加到合并的序列中
        while(p2<=right) tmp[k++]=a[p2++];//同上

        //复制回原素组
        for (int i = left; i <=right; i++)
            a[i]=tmp[i];
    }

    public static void mergeSort(int [] a,int start,int end, int[] tmp){
        if(start<end){//当子序列中只有一个元素时结束递归
            int mid=(start+end)/2;//划分子序列
            mergeSort(a, start, mid, tmp);//对左侧子序列进行递归排序
            mergeSort(a, mid+1, end, tmp);//对右侧子序列进行递归排序
            merge(a, start, mid, end, tmp);//合并
        }
    }

     public static void main(String[] args) {
         //int[] arr = {8, 4, 5, 7 ,1, 3, 6, 2};
         int[] arr = {4, 5, 7, 8, 1, 2, 3, 6};
         int[] temp = new int[arr.length];

         mergeSort(arr, 0, arr.length - 1 , temp);
         System.out.println(Arrays.toString(arr));
     }
}
