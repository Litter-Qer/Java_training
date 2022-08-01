import lombok.extern.slf4j.Slf4j;
import pojo.User;

import java.io.FileReader;

@Slf4j(topic = "c.mainTest")
public class mainTest {
    public static void main(String[] args) throws InterruptedException {
        log.error("Start testing....");

//        simpleTest();

//        for (int i = 0; i < 5; i++) {
//            interruptTest();
//        }

        joinTest();

    }

    public static void simpleTest() {
        MyThread testThread = new MyThread();
        testThread.start();
    }

    public static void interruptTest() {
        User user = new User();
        Thread t1 = new Thread(() -> {
            synchronized (user) {
                try {
                    user.wait();
                    System.out.println("user gets it!");
                } catch (InterruptedException e) {
                    System.out.println("Interrupt gets the lock! ");
                    Thread.currentThread().interrupt(); // 由于自己已经被打断了，所以会归零
                }
            }
        });

        t1.start();

        // 开启一个新的线程，尝试打断之前的线程
        new Thread(() -> {
            synchronized (user) {
                t1.interrupt();
                user.notify();
            }
        }).start();
    }

    public static void joinTest() {
        Thread t1 = new Thread(() -> {
            log.error("Starting t1 (self-defined name)");
            try {
                log.error("t1 首次休眠 5s");
                Thread.sleep(5000);
                log.error("t1 开始第二次休眠 1s");
                Thread.sleep(1000); // 休息1秒
                log.error("t1 结束休眠 1s");
            } catch (InterruptedException e) {
                System.out.println("t1 gets interrupted");
            }
            log.error("结束t1");
        });

//        t1.start();    // 开启线程

        Thread t2 = new Thread(() -> {
            log.error("Starting t2 (self-defined name)");
            try {
                log.error("t1 开始join");
                t1.join(); // 阻塞t2让它必须等待t1结束
            } catch (InterruptedException e) {
                System.out.println("t2被中断");
            }
            log.error("结束t2");
        });

        t1.start();    // 开启线程
        t2.start();

    }
}

