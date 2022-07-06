package task_2604;

import java.text.MessageFormat;

public class MultiplicationTable {
    public void printMT() {
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j <= i; j++) {
                //1.尝试使用 idea 查看编译好的 class 反编译的结果，以及使用 jclasslib 插件查看编译好的 class 内容，思考字符串 + 底层是如何实现的，以及为啥管这个叫语法糖
                //2.思考字符串拼接 String.append 以及 StringBuilder 的区别
                //3.使用 java.text.MessageFormat.format 优化这里的代码的可读性
                System.out.print(MessageFormat.format("{0}x{1}={2} ",i,j,i*j));
            }
            System.out.println();
        }
    }

    public void string2() {
        String s1 = "12" + "asd";
        String s2 = "12" + new String("asd");
        String s3 = "12" ;
        String s4 = "asd";
        String s5 = s3 + s4;
        String s6 = 20 + 10 + "12" + 20 + 15;

    }
}
