package cn.edu.datasource.search;

/**
 * @Author: DDG
 * @Date: 2020/4/26 10:01
 * @Description:
 */
public class InsertSearch {
    public static void main(String[] args) {
        int[] arr = {1, 3, 5, 6, 7, 7,  9};
        //int i = insertSearch(arr, 8);
        int i1 = insertSearch(arr, 0, arr.length - 1, 8);
        System.out.println(i1);
        //System.out.println(i);
    }
    // 插值查找的思想 和 二分查找的思想是一致的 (需要注意的就是 插值查找的 在比较均匀的时候效率比较的高
    // 但是 在不均匀的时候 效率 不一定浩宇折半查找)
    // 但是 mid 是自适应的
    public static int insertSearch(int[] arr, int value){
        int l = 0;
        int r = arr.length - 1;
        int mid;
        while (l < r && value >= arr[l] && value <= arr[r] ) {
            // 插值查找的算法  和 二分查找的思想是一致的 但是就是一点不同就是 mid 的取值方式不同
            mid = l + (r - l) * (value - arr[l]) / (arr[r] - arr[l]);
            if (value < arr[mid]) {
                r = mid - 1;
            } else if (value > arr[mid]) {
                l = mid + 1;
            } else {
                return mid;
            }

        }
        return -1;
    }
    public static int insertSearch(int[] arr, int left, int right, int value) {

        if (left >= right || value < arr[0] || value > arr[arr.length - 1]){
            return -1;
        }
        int mid = left + (right - left) * (value - arr[left]) / (arr[right] - arr[left]);
        int midVal = arr[mid];
        // 要是没有找到 后面的两个条件是防止越界的
        if (value < midVal) {
            return insertSearch(arr, left, mid - 1, value);
        } else if (value > midVal) {
            return insertSearch(arr, mid + 1, right, value);
        } else {
            return mid;
        }

    }
}
