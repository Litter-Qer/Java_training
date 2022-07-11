public class StringExample1 {
    public static void main(String[] args) {
        String s1 = new String(new char[] {'a','b','c'});
        String s2 = new String(new byte[] {97,98,99});
        String s3 = new String(s1);
        String s4 = "abc";
        String s5 = "abc";
        System.out.println(s1 == s2);
        System.out.println(s2 == s3);
        System.out.println(s1 == s3);
        System.out.println(s1 == s4);
        System.out.println(s4 == s5);
    }
}
