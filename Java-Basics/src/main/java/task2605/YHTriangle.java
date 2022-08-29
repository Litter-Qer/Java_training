import java.text.MessageFormat;

public class YHTriangle {
    final static int HEIGHT = 10;

    public static void main(String[] args) {
        YHTriangle t = new YHTriangle();
        t.yhTriangle(HEIGHT);
    }

    public void yhTriangle(int height){
        int[][] old = new int[height][];
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < height; i++) {
            old[i] = new int[i+1];  // 每一列的长度和它的高度一致
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < old[i].length; j++) {
                int num = 1;
                for (int k = 1; k <= j; k++) {
                    num = num * (i-k+1) / k;    // 杨辉三角公式
                }
                old[i][j] = num;
                str.append(MessageFormat.format("{0} ",old[i][j]));
            }
            str.append("\n");
        }
        System.out.println(str);
    }
}
