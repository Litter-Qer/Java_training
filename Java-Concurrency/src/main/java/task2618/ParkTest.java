package task2618;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

@Slf4j
public class ParkTest {

    public static void main(String[] args) throws InterruptedException {
        test1();
    }

    public static void test1() throws InterruptedException {
        Thread t1 = new Thread(()->{
            log.debug("t1 started");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.debug("t1 park");
            LockSupport.park();
            log.debug("t1 resume");
        },"t1");

        t1.start();
        TimeUnit.SECONDS.sleep(1);
        log.debug("unpark....");
        LockSupport.unpark(t1);
    }
}