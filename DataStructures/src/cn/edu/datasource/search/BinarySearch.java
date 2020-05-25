package cn.edu.datasource.search;

/**
 * @Author: DDG
 * @Date: 2020/4/25 17:26
 * @Description:
 */
public class BinarySearch {
    public static void main(String[] args) {
        int[] arr = {1, 2, 4, 5, 7, 8, 11};
        int i1 = binarySearch(arr, 9);
        System.out.println(i1);
        int i = binarySearch2(arr ,  0, arr.length -1, 9);
        System.out.println(i);
    }

    public static int binarySearch(int[] arr, int value) {
        int l = 0;
        int r = arr.length - 1;
        int mid;
        int count = 1;
        while (l < r) {
            mid = (l + r) / 2;
            if (value < arr[mid]) {
                r = mid - 1;
            } else if (value > arr[mid]) {
                l = mid + 1;
            } else {
               return mid;
            }
            System.out.println(count ++);
        }
        return -1;
    }

    public static int binarySearch2(int[] arr, int left, int right, int value) {
        int mid = (left + right) / 2;
        int midVal = arr[mid];
        // 要是没有找到
        if (left > right){
            return -1;
        }
        if (value < midVal) {
            return binarySearch2(arr, left, mid - 1, value);
        } else if (value > midVal) {
            return binarySearch2(arr, mid + 1, right, value);
        } else {
            return mid;
        }

    }
}
