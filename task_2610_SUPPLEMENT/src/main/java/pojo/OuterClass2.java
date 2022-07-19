package pojo;

public class OuterClass2 {
    public OuterClass2() {
    }

    String a = "string a";

    public void showInfo(){
        System.out.println("调用showInfo方法------1");
        final int b = 20;
        class SmallClass {
            public static void suse(){
//                System.out.println("这是sc的suse " + a);
                System.out.println("这是sc的suse " + b);
            }
            public void puse(){
                System.out.println("这是sc的suse " + a);
                System.out.println("这是sc的puse " + b);
            }
        }
        SmallClass sc = new SmallClass();
        sc.puse();
        SmallClass.suse();
    }

    public void showInfo2(){
        System.out.println("调用showInfo方法-------2");
//        SmallClass.suse();
    }
    public class InnerClass2 {
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
