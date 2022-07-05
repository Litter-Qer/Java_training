package pojo;

public class Trapezoid extends Shape{
    final private int top;
    final private int bottom;
    final private int height;

    public Trapezoid(int top, int bottom) {
        super();
        this.height = Math.abs(top-bottom)+1;

        int tempTop = top;
        int tempBot = bottom;
        if (top < bottom) {
            tempBot = top;
            tempTop = bottom;
        }
        this.top = tempBot;
        this.bottom = tempTop;
    }

    public int getTop() {
        return top;
    }

    public int getBottom() {
        return bottom;
    }

    @Override
    public void printLeft()
    {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < top+i; j++) {
                System.out.print(getLabel());
            }
            System.out.println();
        }
        System.out.println(); //图形之间空行
    }
    @Override
    public void printRight()
    {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < i; j++) {
                System.out.print(" ");
            }
            for (int j = 0; j < this.bottom-i; j++) {
                System.out.print(getLabel());
            }
            System.out.println();
        }
        System.out.println(); //图形之间空行
    }
}
