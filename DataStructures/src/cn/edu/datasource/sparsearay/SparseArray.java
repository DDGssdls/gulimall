package cn.edu.datasource.sparsearay;

/**
 * 稀疏数组
 */
public class SparseArray {
    public static void main(String[] args) {

        //创建一个原始的二维数组
        //0表示没有棋子， 1表示黑子 2 表示蓝子。
        int[][] chessArr  = new int[11][11];
        chessArr[1][2] = 1;
        chessArr[2][3] = 2;
        //输出原始的二维数组：
        System.out.println("原始的二维数组");
        for (int[] arr:chessArr
        ) {
            for (int item:arr
            ) {
                System.out.print(item+"\t");
            }
            System.out.println();
        }
        int sum = 0;
        for (int i = 0; i < 11 ; i++) {
            for (int j = 0; j < 11; j++) {
                if(chessArr[i][j] != 0){
                    sum++;
                }
            }
        }
        System.out.println("sum="+sum);
        System.out.println("#############");
        //创建相对应得稀疏数组
        int[][] sparseArr = new int[sum+1][3];
        sparseArr[0][0] = 11;
        sparseArr[0][1] = 11;
        sparseArr[0][2] = sum;
        //遍历二维数组 将非零的值存放到稀疏数组中。
        int count = 1;
        for (int i = 0; i < 11 ; i++) {
            for (int j = 0; j < 11; j++) {
                if(chessArr[i][j] != 0){
                    sparseArr[count][0] = i;
                    sparseArr[count][1] = j;
                    sparseArr[count][2] = chessArr[i][j];
                    count++;
                }
            }
        }
        for(int[] nums: sparseArr){
            for (int num:nums){
                System.out.print(num+"\t");
            }
            System.out.println();
        }
        // 将稀疏数组转换成为二维数组
        //1 是先读取稀疏数组中的第一行第一列的值 ，和第一行第二列的值创建新的二维数组
        int[][] newChessArr = new int[sparseArr[0][0]][sparseArr[0][1]];
        //2 将第二行到最后行的数据添加到二维数组中。
        for (int i = 1; i < sparseArr.length ; i++) {
            newChessArr[sparseArr[i][0]][sparseArr[i][1]] = sparseArr[i][2];
        }
        for (int[] arr:newChessArr
        ) {
            for (int item:arr
            ) {
                System.out.print(item+"\t");
            }
            System.out.println();
        }
    }
}
