package task2610;

import interfaces.lambda.LambdaImpl;
import interfaces.lambda.LambdaInterface;
import interfaces.lambda.LambdaInterface2;

public class InterfaceTest {
    public static void main(String[] args) {
//        System.out.println(MyInterface.name2);

        // 正常的实例化然后调用
        LambdaImpl lpl = new LambdaImpl();
        lpl.compare();

        // 通过lambda表达式实现
        LambdaInterface lpl1 = ()->{
            System.out.println("通过lambda表达式实现的compare");
        };
        lpl1.compare();

        // 无法通过直接赋值的方式来改写lpl
//         lpl = ()->{
//            System.out.println("通过lambda表达式实现的compare");
//        };
//        lpl.compare();


        // 可以通过直接赋值的方式改写lpl1
        lpl1 = ()->{
            System.out.println("通过lambda表达式实现的compare --- 2");
        };
        lpl1.compare();

        // 有返回值有参数
        LambdaInterface2 lpl2 = (String str)->{
            System.out.println("有参有返回值，不实例化" + str);
            return str;
        };
        String str = lpl2.compare("abc");
        System.out.println(str);
    }
}
