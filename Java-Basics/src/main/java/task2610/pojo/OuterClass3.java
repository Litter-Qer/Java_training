package task2610.pojo;

public class OuterClass3 {
    public OuterClass3() {
    }

    String a = "string a";

    public static class InnerClass3 {
        public static final String a = "Success";
        public static Integer b = 10;
        final static Integer c = 5;

        public static void show(){
            System.out.println("This is a static method ---- InnerClass2");
            System.out.println(a);
            System.out.println(b);
            System.out.println(c);
        }
    }
}
