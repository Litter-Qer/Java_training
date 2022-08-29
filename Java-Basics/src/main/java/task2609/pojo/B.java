package pojo;

public class B extends A {
    static {
        System.out.println("这是B的静态块");
    }

    protected int id;

    public B() {
        this.id = 20;
        System.out.println("B的构造器");
        System.out.println("A: " + super.id + "\nB: " + this.id);
    }

//    public static void print(){
//        System.out.println("我是B的print方法");
//    }

}
