package pojo;

public class C extends B {
    static {
        System.out.println("这是C的静态块");
    }

    private int id;

    public C() {
        this.id = 30;
        System.out.println("C的构造器");
        System.out.println("B: " + super.id + "\nC: " + this.id);
    }

//    public static void print(){
//        System.out.println("我是B的print方法");
//    }

}
