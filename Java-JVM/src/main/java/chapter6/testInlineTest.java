package chapter6;

public class testInlineTest {
    public static void foo(Object obj) {
        if (obj != null) {
            System.out.println("do something");
        }
    }

    public static void testInline(String[] args) {
        Object obj = null;
        foo(obj);
    }
}
