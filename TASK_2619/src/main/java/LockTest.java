import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class LockTest {
    private static final ReentrantLock reentrantLock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
//        test1();
//        test3();
        test4();
    }

    public static void test1() {
        reentrantLock.lock();
        try {
            log.debug("This is method 1");
            test2();
        } finally {
            reentrantLock.unlock(); // 释放锁
        }
    }

    private static void test2() {
        reentrantLock.lock();
        try {
            log.debug("This is method 2");
        } finally {
            reentrantLock.unlock(); // 释放锁
        }
    }

    private static void test3() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                // 如果没有竞争那么此方法就会获取lock对象的锁
                // 如果有竞争就进入阻塞队列，可以被其它线程用interrupt方法打断。
                log.debug("尝试获取锁");
                reentrantLock.lockInterruptibly();
            } catch (InterruptedException e) {
                log.debug("没有获得锁, 直接返回");
                e.printStackTrace();
                return;
            }
            try {
                log.debug("获取到锁了");
            } finally {
                reentrantLock.unlock();
            }
        }, "t1");

        reentrantLock.lock();
        t1.start();
        TimeUnit.SECONDS.sleep(1);
        log.debug("t1状态 - {}", t1.getState());
        log.debug("打断 t1");
        t1.interrupt();
    }

    private static void test4() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Thread t1 = new Thread(() -> {
            // 尝试获取锁
            try {
                if (!lock.tryLock(2,TimeUnit.SECONDS)) {
                    log.debug("尝试1s，获取不到锁");
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.debug("没有获取到锁");
                return;
            }

            try {
                log.debug("获得到锁");
            } finally {
                lock.unlock();
            }
        }, "t1");

        lock.lock();
        log.debug("main 获得锁");
        t1.start();
        TimeUnit.SECONDS.sleep(1);
        lock.unlock();
        log.debug("main 释放了锁");
    }
}
