import java.util.Arrays;

public class Array2d {
    public static void main(String[] args) {
        int[][] arr = {{80,90,75,61,68},{45,68,79,75,77},{40,58,57,91,82}};
        Array2d a2d = new Array2d();
        int[] sum1 = a2d.scoreSum(arr);
        int[] sum2 = a2d.scoreSumStream(arr);
        int[] sum3 = a2d.scoreSumStream2(arr);
        System.out.println(Arrays.toString(sum1));
        System.out.println(Arrays.toString(sum2));
        System.out.println(Arrays.toString(sum3));
    }

    /**
     * 计算每一个小组的分数，并且记录到一个新的array中
     * @param scores 一个二维数组，列表示分数，行表示小组
     * @return 一个一维数组，按照index表示每个组的总分
     */
    public int[] scoreSum(int[][] scores){
        int height = scores.length;
        int len = scores[height-1].length;

        //遍历所有的分数
        int[] temp = new int[height];
        for (int i = 0; i < height; i++) {
            int sum = 0;
            for (int j = 0; j < len; j++) {
                sum += scores[i][j];
            }
            temp[i] = sum;
        }
        return temp;
    }

    /**
     * 使用stream计算小组的总分
     * @param scores 一个二维数组，列表示分数，行表示小组
     * @return 一个一维数组，按照index表示每个组的总分
     */
    public int[] scoreSumStream(int[][] scores){
        int height = scores.length;

        //遍历所有的分数
        int[] temp = new int[height];
        for (int i = 0; i < height; i++) {
            temp[i] = Arrays.stream(scores[i]).sum();
        }
        return temp;
    }

    /**
     * 使用foreach和stream计算小组的总分
     * @param scores 一个二维数组，列表示分数，行表示小组
     * @return 一个一维数组，按照index表示每个组的总分
     */
    public int[] scoreSumStream2(int[][] scores){
        int height = scores.length;

        //遍历所有的分数
        int[] temp = new int[height];
        int i = 0;
        for (int[] row: scores) {
            temp[i] = Arrays.stream(row).sum();
            i++;
        }
        return temp;
    }
}
