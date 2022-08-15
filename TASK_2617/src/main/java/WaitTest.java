import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WaitTest {
    static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
//        test1();
        test2();
    }

    private static void test2() throws InterruptedException {
        Thread t1 = new Thread(()->{
            log.debug("Thread 1 starting");
            synchronized (lock){
                try {
                    lock.wait(20000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("Other stuff to do...");
            }
        },"t1");

        Thread t2 = new Thread(()->{
            log.debug("Thread 2 starting");
            synchronized (lock){
                try {
                    lock.wait(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("Other stuff to do...");
            }
        },"t2");

        t1.start();
        t2.start();

        Thread.sleep(3000); // 确保线程1和2都已进入WAITING
        log.debug("t1: {} t2: {}",t1.getState(),t2.getState());

        log.debug("notify threads");
        synchronized (lock){
            lock.notify();
        }
    }

    public static void test1() {
        try {
            lock.wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
