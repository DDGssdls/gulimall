package cn.edu.datasource.recursion;

import java.sql.SQLOutput;
import java.util.Arrays;

public class EightQueen {
// p47 - p49
    int max = 8;
    int count = 0;
    int judgetCount = 0;
    int[] arr = new int[max];
    public static void main(String[] args) {

        // 这里是八皇后的为题：
        // 首先是创建棋盘 这里使用的一维数组 每个皇后都会占一行 对应的下标表示第几行 也表示的是第几个皇后
        int count = 1;
        EightQueen eightQueen = new EightQueen();
        eightQueen.check(0);
        System.out.println(eightQueen.count);
        System.out.println(count);
        System.out.println(eightQueen.judgetCount);

    }

    // 传入的参数表示的是第n个皇后 是否是发生冲突
    private  boolean judgement(int n){
        judgetCount ++;
        if (n < 0){
            return false;
        }
        for (int i = 0; i < n; i++) {
            //Math.abs(n - i) == Math.abs(arr[n] - arr[i]) 表示的是在一条斜线上 其实是两条斜线
            if (arr[i] == arr[n] ||Math.abs(n - i) == Math.abs(arr[n] - arr[i])){
                return false;
            }
        }
        return true;
    }
    //编写方法判断放置皇后
    private void check(int n){
        if (n == max){
            // 表示是的是前边的皇后都能放置 直接退出递归
            print();
            // 表示的是一种解法
            return;
        }
        for (int i = 0; i < max; i++) {
            // 首先将当前的皇后放置在第一列上
            arr[n] = i;
            // 判断是否是冲突
            if(judgement(n)){// 方法中返回true 表示的是不发生冲突
                // 就放置下一个皇后
                check(n + 1);
            }
            // 发生冲突 就将皇后放置到后面的一个位置中
        }

    }
    private void print(){
        count++;
        System.out.println(Arrays.toString(arr));;

    }
}
