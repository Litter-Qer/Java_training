import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);

        new Thread(() -> {
            log.debug("start working...");
            try {
                TimeUnit.SECONDS.sleep(1);
                latch.countDown();
                log.debug("Done");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1").start();

        new Thread(() -> {
            log.debug("start working...");
            try {
                TimeUnit.SECONDS.sleep(2);
                latch.countDown();
                log.debug("Done");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t2").start();

        new Thread(() -> {
            log.debug("start working...");
            try {
                TimeUnit.SECONDS.sleep(3);
                latch.countDown();
                log.debug("Done");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t3").start();

        log.debug("waiting for all threads");
        latch.await();
        log.debug("All done");
    }
}
