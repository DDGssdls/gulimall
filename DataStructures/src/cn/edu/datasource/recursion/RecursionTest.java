package cn.edu.datasource.recursion;

public class RecursionTest {
    public static void main(String[] args) {
        test(12);
        //System.out.println(i);
    }

    public static void test(int n){

        System.out.println("n="+ n);

        if (n > 2){
            test(n - 1);
        }

    }

    public static int test1(int n){
        if (n == 1){
            return 1;
        }
        return n * test1(n - 1);
    }

}
