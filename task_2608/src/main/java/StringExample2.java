public class StringExample2 {
    public static void main(String[] args) {

        String s1 = "abc";
        String s2 = new String(new char[] {'a','b','c'});
        String s3 = s2.intern();

        System.out.println(s1 == s2);
        System.out.println(s1 == s3);
        System.out.println(s2 == s3);
    }
}
