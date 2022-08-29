package task2614.test;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

@Slf4j
public class CreateTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        test1();
        test2();
    }
    
    public static void test1(){
        Runnable task1 = ()-> log.debug("runnable创建的线程");
        Thread t = new Thread(task1, "runnable Thread");
        t.start();
    }

    public static void test2() throws ExecutionException, InterruptedException {
        FutureTask<Integer> task2 = new FutureTask<>(()->{
            log.debug("task2");
            return 100;
        });

        new Thread(task2,"t2").start();

        Integer rs = task2.get();
        log.debug("结果是 {}",rs);
    }
}

