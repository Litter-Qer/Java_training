import java.util.Arrays;

public class FinalExample {
    public static void main(String[] args) {
        // int 不可被修改
        final int a = 6;
//        a = 7;

        // array 可被‘修改’
        final int[] b = new int[2];
        System.out.println(Arrays.toString(b));
        System.out.println(Arrays.toString(b));

        // String 可被‘修改’
        String c = "abc";
        c = "123";
        b[0] = 1;
        System.out.println(c);
    }
}
