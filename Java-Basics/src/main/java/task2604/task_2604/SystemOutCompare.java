package task2604.task_2604;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

public class SystemOutCompare {

    final static int HEIGHT = 2;
    final static int BOTTOM = 5;
    final static String LABEL = "*";

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder().include(SystemOutCompare.class.getSimpleName()).forks(2).build();
        new Runner(opt).run();
    }

    @Benchmark
    @Fork(value = 1,warmups = 2)
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void test1(){
        // 1. 思考下对于 System.out 一次写入 10 个字符与 10 次每次写 1 个字符哪个效率更高
        // 2. 使用 jmh 验证你的猜想
        // 3. 思考下如何减少 System.out 的次数
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < i; j++) {
                System.out.print(" ");
            }
            for (int j = 0; j < BOTTOM; j++) {
                System.out.print(LABEL);
            }
            System.out.println();
        }
        System.out.println(); //图形之间空行
    }
}
