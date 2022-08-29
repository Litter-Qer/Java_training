package task2618;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class DeadLockTest {
    private final static Object a = new Object();
    private final static Object b = new Object();

    public static void main(String[] args) throws InterruptedException {
        test1();
    }

    public static void test1() throws InterruptedException {
        new Thread(() -> {
            synchronized (a){
                log.debug("Thread 1 获得锁 a");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (b){
                    log.debug("Thread 1 获得锁 b");
                }
            }
        },"t1").start();

        new Thread(() -> {
            synchronized (b){
                log.debug("Thread 2 获得锁 b");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (a){
                    log.debug("Thread 2 获得锁 a");
                }
            }
        },"t2").start();
    }
}