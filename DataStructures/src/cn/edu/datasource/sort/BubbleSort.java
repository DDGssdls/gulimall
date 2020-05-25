package cn.edu.datasource.sort;

import java.util.Arrays;

public class BubbleSort {
    //p 57
    //static方法中不能定义static变量
    // 方法中使用的内存是在进行调用的时候才进行使用 执行完方法就释放
    // 所以使用static不能使用 static是类层次上的东西
    // 获取时间戳的三种方式Calendar.getInstance().getTimeInMillis(); System.CurrentTimeMillis()
    public static void main(String[] args) {
        int[] nums = {1, 3, 9, 10 , 20};
        long begin = System.currentTimeMillis();
        bubbleSort(nums);
        long end = System.currentTimeMillis();
        System.out.println(end - begin);
        System.out.println("---------------");
        bubbleSortAdvance(nums);
    }
    public void  test(){
        //test();
    }

    public static void bubbleSort(int[] nums){
        // 时间复杂度是 on方
        int num = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            // 内层中的循环就是将最大的数字找出来排到数组的末尾
            for (int j = 0; j < nums.length - i - 1; j++) {
                // 这里是判断 和进行交换的逻辑 前面的大 进行交换就是升序 前面的小进行交换就是降序
                if (nums[j] > nums[j+1]){
                    num = nums[j];
                    nums[j] = nums[j+1];
                    nums[j+1] = num;
                }
            }
            System.out.println(Arrays.toString(nums));
        }


    }

    public static void bubbleSortAdvance(int[] nums){
        // bubble sort进行优化的方法就是 设置一个flag
        // 如果在一个外循环中都没有进行位置上的变化 就表示已经排好序了
        // 这样就能提前的终止循环
        int num = 0;
        boolean flag = false;
        for (int i = 0; i < nums.length - 1; i++) {
            //boolean flag = false; 这样的形式会创建多次的对象 不是非常的好
            // 内层中的循环就是将最大的数字找出来排到数组的末尾
            for (int j = 0; j < nums.length - i - 1; j++) {
                // 这里是判断 和进行交换的逻辑 前面的大 进行交换就是升序 前面的小进行交换就是降序
                if (nums[j] > nums[j+1]){
                    num = nums[j];
                    nums[j] = nums[j+1];
                    nums[j+1] = num;
                    flag = true;
                }
            }
            System.out.println(Arrays.toString(nums));

            /*if (!flag){
                break;
            }*/
            if (!flag){
                break;
            }else {// 如果flag变动的话需要在每次内层循环之后进行修改 flag 的值为false
                flag = false;
            }
        }

    }

}
