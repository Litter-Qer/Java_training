package task2610.apackage;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Fork(1)
@Warmup(iterations = 2, time = 1)
@Measurement(iterations = 3, time = 1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class ArrayListExample2 {
    final static int ITERATIONS = 10000;
    static List<Integer> al = new ArrayList<>();
    static List<Integer> ll = new LinkedList<>();

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(ArrayListExample2.class.getSimpleName())
                .resultFormat(ResultFormatType.JSON)
                .build();
        new Runner(opt).run();
    }

    @Benchmark
    public void test1(){
        for (int i = 0; i < ITERATIONS; i++) {
            al.add(i);
        }
    }

    @Benchmark
    public void test2(){
        for (int i = 0; i < ITERATIONS; i++) {
            ll.add(i);
        }
    }

}
