package cn.edu.datasource.recursion;



public class MIGong {
    public static void main(String[] args) {

        // 使用二维数组 进行迷宫的搭建
        int[][] map = new int[8][7];
        // 使用1 表示墙
        for(int i = 0;i < 7; i++ ){
            map[0][i] = 1;
            map[7][i] = 1;
        }
        // 使用for循环
        for (int i = 1; i < 7; i++) {
            map[i][0] = 1;
            map[i][6] = 1;
        }
        map[3][1] = 1;
        map[3][2] = 1;
        // 进行地图的输出
        for (int i = 0; i <8 ; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }

        boolean b = setWay1(map, 1, 1);
        System.out.println(b);
        // 进行地图的输出
        for (int i = 0; i <8 ; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }
    // 参数表示的是传入的地图 i 和 j 表示的是横纵坐标 如果小球能到（6，5）的位置表示有通路
    // 当二维数组中的值为零时 表示的是没有走过 当二维数组中的值为二时，表示的是走过
    // 数组中的值为三的时候 表示的是走过了 但是 不通
    // 小球走的策略是 下 右 上 左
    public static boolean setWay(int[][] map, int i, int j){
        if(map[6][5] == 2){
            return true;
        }else{
            if (map[i][j] == 0){// 表示的是没有走过就需要按照策略进行走
                // 假定都能走通
                map[i][j] = 2;
                // 开始按照策略进行走 首先是向下走
                if (setWay(map, i + 1, j)){
                    return true; //  表示的是能向下走
                }else if (setWay(map, i, j + 1)){
                    return true; // 表示的是能向右走
                }else if (setWay(map, i - 1, j )){
                    return true; // 表示的是能向上走
                }else if (setWay(map, i , j - 1)){
                    return true; // 表示的是能向左走
                }else{
                    map[i][j] = 3;
                }


            }else{// 表示的是map 不是零 可能是 1 2 3
                return false;
            }

        }


        return true;
    }

    // 小球走的策略是 上 右 下 左
    public static boolean setWay1(int[][] map, int i, int j){
        if(map[6][5] == 2){
            return true;
        }else{
            if (map[i][j] == 0){// 表示的是没有走过就需要按照策略进行走
                // 假定都能走通
                map[i][j] = 2;
                // 开始按照策略进行走 首先是向下走
                if (setWay1(map, i - 1, j)){
                    return true; //  表示的是能向下走
                }else if (setWay1(map, i, j + 1)){
                    return true; // 表示的是能向右走
                }else if (setWay1(map, i + 1, j )){
                    return true; // 表示的是能向上走
                }else if (setWay1(map, i , j - 1)){
                    return true; // 表示的是能向左走
                }else{
                    map[i][j] = 3;
                }
            }else{// 表示的是map 不是零 可能是 1 2 3
                return false;
            }
        }
        return true;
    }
}
