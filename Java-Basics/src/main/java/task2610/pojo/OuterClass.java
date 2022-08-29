package task2610.pojo;

public class OuterClass {
    final static String a = "final static string a";
    static String b = "static string b";
    private String c = "private string c";
    protected String d = "protected string d";

    public OuterClass() {
    }

    public OuterClass(String c) {
        this.c = c;
    }

    public OuterClass(String c, String d) {
        this(c);
        this.d = d;
    }

    public InnerClass createInner() {
        return new InnerClass();
    }

    public class InnerClass {
        int c = 5;
        int a = 10;
        String y = "inner class string y";
        public void info(){
            System.out.println("外部c变量： "+ OuterClass.this.c);
            System.out.println("内部c变量： "+ InnerClass.this.c);
            System.out.println("外部a变量： "+ OuterClass.a);
            System.out.println("内部a变量： "+ InnerClass.this.a);
            System.out.println("This is an inner class");
        }
    }

    private class PrivateClass {

    }

}
