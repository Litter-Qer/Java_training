package test;
import lombok.extern.slf4j.Slf4j;

import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

@Slf4j
public class ParkTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        test1();
    }

    public static void test1() throws InterruptedException {
        Thread t1 = new Thread(()->{
            log.debug("park....");
            LockSupport.park();
            log.debug("unpark...");
            log.debug("打断状态 {}", Thread.interrupted());
            LockSupport.park();
            log.debug("unpark...");
        },"t1");
        t1.start();

        Thread.sleep(1000);
        t1.interrupt();
    }
}

