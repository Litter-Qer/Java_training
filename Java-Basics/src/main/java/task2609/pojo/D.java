package pojo;

public class D extends B {
    static {
        System.out.println("这是D的静态块");
    }

    private int id;

    public D() {
        this.id = 40;
        System.out.println("D的构造器");
        System.out.println("B: " + super.id + "\nD: " + this.id);
    }

//    public static void print(){
//        System.out.println("我是B的print方法");
//    }

}
