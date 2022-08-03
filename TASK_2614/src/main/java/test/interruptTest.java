package test;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Slf4j
public class interruptTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        test1();
    }
    
    public static void test1() throws InterruptedException {
        Thread t1 = new Thread(()->{
            log.debug("进入睡眠");
            try {
                Thread.sleep(5000); // 也可以换成join或者wait
            } catch (InterruptedException e) {
                log.debug("t1 打断标记 {}", Thread.currentThread().isInterrupted());
                e.printStackTrace();
            }
        },"t1");

        t1.start();
        Thread.sleep(1000);
        t1.interrupt();
        log.debug("t1 打断标记 {}", t1.isInterrupted());
        Thread.sleep(1000);
        log.debug("t1 打断标记 {}", t1.isInterrupted());
    }
}

