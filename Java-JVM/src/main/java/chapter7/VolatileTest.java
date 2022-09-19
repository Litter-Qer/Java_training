package chapter7;

import java.util.concurrent.TimeUnit;

public class VolatileTest {
    public static volatile int race = 0;

    public static void increase() {
        race++;
    }

    private static final int THREAD_COUNT = 10;

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(()->{
                for (int j = 0; j < 1000; j++) {
                    increase();
                }
            }).start();
        }

        TimeUnit.SECONDS.sleep(12);
//        Thread.currentThread().join();
        System.out.println(race);
    }
}
