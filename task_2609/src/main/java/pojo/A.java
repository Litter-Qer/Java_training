package pojo;

public class A {
    protected int id;

    static {
        System.out.println("这是A的静态块1");
    }

    static {
        System.out.println("这是A的静态块2");
    }

    static {
        System.out.println("这是A的静态块3");
    }

    public A(int id) {
        this.id = id;
    }

    public A() {
        this.id = 10;
        System.out.println("A的构造器");
        System.out.println("A: " + this.id);
    }

    public static void print() {
        System.out.println("我是A的print方法");
    }
}
