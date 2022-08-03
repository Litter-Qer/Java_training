package test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class sleepTest {
    public static void main(String[] args) throws InterruptedException {
//        test1();
        test2();
    }


    private static void test1() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "t1");

        t1.start();
        log.debug("Thread t1 状态 {}", t1.getState().name());

        Thread.sleep(500);
        log.debug("Thread t1 状态 {}", t1.getState().name());

        Thread.sleep(1000);
        log.debug("Thread t1 状态 {}", t1.getState().name());
    }

    private static void test2() throws InterruptedException {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    log.debug("t2 开始休眠 1s");
                    Thread.sleep(10000); // t2 休眠1秒
                    log.debug("t2 休眠完成 {}", Thread.currentThread().getState().name());
                    if (Thread.currentThread().isInterrupted()) log.debug("线程被中断了");
                    else log.debug("线程被还原了");
                } catch (InterruptedException e) {
                    log.debug("t2 被叫醒");
                    log.debug("t2 叫醒后状态 {}", Thread.currentThread().getState().name());
                }
            }
        };
        Thread t2 = new Thread(r, "t2");

        t2.start();
        log.debug("t2 状态 {}", t2.getState().name());    // 查看t2的状态

        Thread.sleep(200); // 主线程 休眠1秒
        log.debug("主线程打断睡眠");
        t2.interrupt();

//        Thread.sleep(0,1);
        t2.yield();
        Thread.sleep(1000); // 主线程再次休眠1秒
        log.debug("t2 状态 {}", t2.getState().name());    // 再次查看t2的状态
    }
}

