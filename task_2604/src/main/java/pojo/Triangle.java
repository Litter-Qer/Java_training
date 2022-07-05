package pojo;

import java.security.PrivilegedAction;

public class Triangle extends Shape {
    final int height;

    public Triangle(int height) {
        super();
        this.height = height;
    }

    public int getHeight() {
        return height;
    }
    @Override
    public void printLeft()
    {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < i+1; j++) {
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
            for (int j = 0; j < height-(i+1); j++) {
                System.out.print(" ");
            }
            for (int j = 0; j < i+1; j++) {
                System.out.print(getLabel());
            }
            System.out.println();
        }
        System.out.println(); //图形之间空行
    }
}
