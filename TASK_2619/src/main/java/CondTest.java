import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class CondTest {
    private static final ReentrantLock lock = new ReentrantLock();
    private static final Condition waitMilkSet = lock.newCondition();
    private static final Condition waitCoffeeSet = lock.newCondition();
    private static boolean hasMilk = false;
    private static boolean hasCoffee = false;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            lock.lock();
            try {
                log.debug("等待咖啡");
                while (!hasCoffee) {
                    try {
                        waitCoffeeSet.await();
                    } catch (InterruptedException e) {
                        log.debug("等待咖啡被打断，555555");
                        e.printStackTrace();
                    }
                }
            } finally {
                lock.unlock();
            }
            log.debug("喝完咖啡，更不要睡了");
        }, "咕咕").start();

        new Thread(() -> {
            lock.lock();
            try {
                log.debug("等待牛奶");
                while (!hasMilk) {
                    try {
                        waitMilkSet.await();
                    } catch (InterruptedException e) {
                        log.debug("等待牛奶被打断，555555");
                        e.printStackTrace();
                    }
                }
            } finally {
                lock.unlock();
            }
            log.debug("心满意足，呼呼大睡");
        }, "嘟嘟").start();

        TimeUnit.SECONDS.sleep(1); // 保证线程进入等待状态
        new Thread(() -> {
            lock.lock();
            try {
                waitMilkSet.signal();
                hasMilk = true;
            } finally {
                lock.unlock();
            }
            log.debug("送完牛奶了，我可以睡了");
        }, "工具人1").start();

        TimeUnit.SECONDS.sleep(1); // 保证线程进入等待状态
        new Thread(() -> {
            lock.lock();
            try {
                waitCoffeeSet.signal();
                hasCoffee = true;
            } finally {
                lock.unlock();
            }
            log.debug("送完咖啡，我还得陪孩子");
        }, "工具人2").start();
    }
}