package task2614.test;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;

@Slf4j
public class StateTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        test1();
    }
    
    public static void test1() throws InterruptedException {
        Thread t1 = new Thread(()->log.debug("这是t1线程"), "t1");  // 空线程

        Thread t2 = new Thread(()->{
            while (true) {}
        }, "t2");

        Thread t3 = new Thread(()->log.debug("这是t3线程"), "t3");

        Thread t4= new Thread(()->{
            synchronized (StateTest.class){
                try {
                    Thread.sleep(100000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "t4");

        Thread t5 = new Thread(()-> {
            try {
                t2.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "t5");

        Thread t6 = new Thread(()->{
            synchronized (StateTest.class){
                try {
                    Thread.sleep(100000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "t6");

        // 开启线程
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();


        Thread.sleep(1000); // 主线程休息

        log.debug("t1 状态 {}", t1.getState().name());
        log.debug("t2 状态 {}", t2.getState().name());
        log.debug("t3 状态 {}", t3.getState().name());
        log.debug("t4 状态 {}", t4.getState().name());
        log.debug("t5 状态 {}", t5.getState().name());
        log.debug("t6 状态 {}", t6.getState().name());
    }

}

