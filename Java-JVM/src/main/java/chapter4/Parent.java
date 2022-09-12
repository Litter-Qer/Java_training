package chapter4;

public class Parent {
    public static int A = 1;
//    public static int D = 2;

    static {
        A = 2;
        D = 3;
    }

    public static int D = 2;

    static class Sub extends Parent {
        public static int B = A;
        public static int C = D;
    }

    public static void main(String[] args) {
        System.out.println(Sub.B);
        System.out.println(Sub.C);
    }
}
