package interfaces.plain;

public interface MyInterface {
    // Fields
    public static final String name = "abc1";
    String name2 = "abc2";

    //
    public abstract void test();

    void test2(int num);

    private void test3() {
        System.out.println("接口中可以实现private方法");
    }

    default void test4()
    {
        test3();
        System.out.println("接口中可以实现默认方法");
    }
}
