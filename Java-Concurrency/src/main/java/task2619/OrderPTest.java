package task2619;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

@Slf4j
public class OrderPTest {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            LockSupport.park(); //
            log.debug("This is t1");
        }, "t1");
        t1.start();

        new Thread(() -> {
            log.debug("This is t2");
            LockSupport.unpark(t1); // 修改标志位
            }, "t2").start();
    }
}