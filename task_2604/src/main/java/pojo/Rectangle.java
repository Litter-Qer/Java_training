package pojo;

public class Rectangle extends Shape {
    private int length;
    private int width;

    public Rectangle() {
        super();
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public void print() {
        super.print();
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.length; j++) {
                System.out.print(getLabel());
            }
            System.out.println();
        }
        System.out.println(); //图形之间空行
    }
}
