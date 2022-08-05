package test;
import ch.qos.logback.core.joran.conditional.ThenAction;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.LockSupport;

@Slf4j
public class MainTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        test1();
    }

    public static void test1() throws InterruptedException {
        Thread t1 = new Thread(()->{
            while (true){
                if (Thread.currentThread().isInterrupted()) break;
            }
            log.debug("t1 已经结束了");
        },"t1");
        t1.start();

        Thread.sleep(1000);
        log.debug("main结束");
    }
}

