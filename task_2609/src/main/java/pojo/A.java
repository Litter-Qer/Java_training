package pojo;

public class A {
    protected int id;

    public A(int id) {
        this.id = id;
    }

    public A() {
        this.id = 10;
    }

    public static void print() {
        System.out.println("我是A的print方法");
    }
}
