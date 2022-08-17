import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class OrderRLTest {
    private static final ReentrantLock lock = new ReentrantLock();
    private static final Condition cond1 = lock.newCondition();
    static boolean t2HasRan = false;

    public static void main(String[] args) {
        new Thread(() -> {
            lock.lock();
            try{
                while (!t2HasRan)
                    try {
                        cond1.await();
                    } catch (InterruptedException e) {
                        log.debug("等待被中断");
                        e.printStackTrace();
                    }
                log.debug("this is thread 1");
            } finally {
                lock.unlock();
            }
        }, "t1").start();

        new Thread(() -> {
            lock.lock();
            try {
                log.debug("This is thread 2");
                t2HasRan = true;
                cond1.signal(); // 唤醒
            } finally {
                lock.unlock();
            }
        }, "t2").start();
    }
}