package test;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import threads.*;

import java.util.Arrays;

@Fork(1)
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 3)
@Measurement(iterations = 5)
@Slf4j
public class SigMulTest {
    static final int SIZE = 100_000_000;
    static int[] ARRAY = new int[SIZE];

    static {
        Arrays.fill(ARRAY, 1);
    }

    @Benchmark
    public int concurrent(){
        addThread1 t1 = new addThread1();
        addThread2 t2 = new addThread2();
        addThread3 t3 = new addThread3();
        addThread4 t4 = new addThread4();

//        log.error("Start threads together");

        t1.start();
        t2.start();
        t3.start();
        t4.start();

//        log.error("Returning the result");

        return t1.getSum() + t2.getSum() + t3.getSum() + t4.getSum();
    }

    @Benchmark
    public int oneCPU(){
        addThread t = new addThread();

//        log.error("Start one thread together");

        t.start();

//        log.error("Returning the result");

        return t.getSum();
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(SigMulTest.class.getSimpleName())
                .result("result.json")
                .resultFormat(ResultFormatType.JSON).build();
        new Runner(opt).run();
    }
}
