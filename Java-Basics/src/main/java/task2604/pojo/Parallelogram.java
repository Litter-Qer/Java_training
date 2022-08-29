package task2604.pojo;

public class Parallelogram extends Shape {
    final private int height;
    final private int bottom;

    public Parallelogram(int height, int bottom) {
        super();
        this.height = height;
        this.bottom = bottom;
    }

    public int getHeight() {
        return height;
    }

    public int getBottom() {
        return bottom;
    }
    @Override
    public void printLeft()
    {
        // 1. 思考下对于 System.out 一次写入 10 个字符与 10 次每次写 1 个字符哪个效率更高
        // 2. 使用 jmh 验证你的猜想
        // 3. 思考下如何减少 System.out 的次数
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < i; j++) {
                System.out.print(" ");
            }
            for (int j = 0; j < this.bottom; j++) {
                System.out.print(getLabel());
            }
            System.out.println();
        }
        System.out.println(); //图形之间空行
    }
    @Override
    public void printRight()
    {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < height-(i+1); j++) {
                System.out.print(" ");
            }
            for (int j = 0; j < bottom; j++) {
                System.out.print(getLabel());
            }
            System.out.println();
        }
        System.out.println(); //图形之间空行
    }
}
