package interfaces;

public class MyInterfaceImpl extends MyAbstractClass {
    public static void main(String[] args) {
        MyAbstractClass cls = new MyInterfaceImpl();
        cls.test();
        cls.test2(1);
    }
    @Override
    public void test() {
        System.out.println(MyInterface.name);
        System.out.println(MyInterface2.name);
        System.out.println(MyInterface3.name);
        System.out.println(MyAbstractClass.name);
        System.out.println(name);
    }

    @Override
    public void test2(int num) {
        test4();
    }
}
