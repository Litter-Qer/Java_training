import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderWnTest {
    static final Object lock = new Object();
    static boolean t2HasRan = false;

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (lock) {
                while (!t2HasRan)
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        log.debug("等待被中断");
                        e.printStackTrace();
                    }
            }
            log.debug("this is thread 1");
        }, "t1").start();

        new Thread(() -> {
            synchronized (lock) {
                log.debug("This is thread 2");
                t2HasRan = true;
                lock.notifyAll(); // 唤醒
            }
        }, "t2").start();
    }
}