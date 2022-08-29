package task2614.test;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Slf4j
public class JoinTest {
    static int a = 0;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        test1();
    }
    
    public static void test1() throws InterruptedException {
        Thread t1 = new Thread(()->{
            try {
                log.debug("t1 start working");
                TimeUnit.SECONDS.sleep(2);
                a = 10;
            } catch (InterruptedException e) {
                log.error("T1 is interrupted");
            }
        },"t1");

        t1.start();
        t1.join();
        log.debug("main 状态{}", Thread.currentThread().getState().name());
        log.debug("r = {}", a);
    }
}

