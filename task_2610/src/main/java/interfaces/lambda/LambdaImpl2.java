package interfaces.lambda;

public class LambdaImpl2 implements LambdaInterface2 {
    @Override
    public String compare(String str) {
        System.out.println("有参有返回值，返回的值是" + str);
        return str;
    }
}
