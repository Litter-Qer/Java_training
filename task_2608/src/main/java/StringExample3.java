public class StringExample3 {
    public static void main(String[] args) {
        String s1 = new String(new char[] {'a','b','c'});
        String s2 = s1.intern();
        String s3 = "abc";

        System.out.println(s1 == s2);
        System.out.println(s1 == s3);
        System.out.println(s2 == s3);
    }
}
