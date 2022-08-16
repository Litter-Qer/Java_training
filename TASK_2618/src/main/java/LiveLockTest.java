import lombok.extern.slf4j.Slf4j;

import javax.print.attribute.HashDocAttributeSet;
import java.util.concurrent.TimeUnit;

@Slf4j
public class LiveLockTest {
    static volatile int count = 10;
    static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        test1();
    }

    public static void test1() throws InterruptedException {
        new Thread(() -> {
            while (count > 0) {
                try {
                    TimeUnit.MILLISECONDS.sleep(20);
                    count--;
                    log.debug("count: {}", count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();

        new Thread(() -> {
            while (count < 20) {
                try {
                    TimeUnit.MILLISECONDS.sleep(20);
                    count++;
                    log.debug("count: {}", count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t2").start();
    }
}