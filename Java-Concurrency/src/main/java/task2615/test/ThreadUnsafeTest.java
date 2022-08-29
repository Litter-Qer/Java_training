package task2615.test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadUnsafeTest {
    static int counter = 0;

    public static void main(String[] args) throws InterruptedException {
        test1();
    }

    private static void test1() throws InterruptedException {
        Thread t1 = new Thread(()-> {
            for (int i = 0; i < 5000; i++) {
                counter++;
            }
        },"t1");

        Thread t2 = new Thread(()-> {
            for (int i = 0; i < 5000; i++) {
                counter--;
            }
        },"t2");

        t1.start();
        t2.start();

        // 等待所有线程完成
        t1.join();
        t2.join();
        log.debug("counter: {}", counter);
    }

}
