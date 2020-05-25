package cn.edu.datasource.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: DDG
 * @Date: 2020/4/25 17:19
 * @Description:
 */
public class SeqSearch {
    public static void main(String[] args) {

        int[] arr = {1, 2, 4, 5, 7, 8, 11};
        int i = seqSearch(arr, -11);
        System.out.println(i);
    }

    public static int seqSearch(int[] arr, int value){
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == value){
                return i;
            }
        }

        return -1;
    }
}
