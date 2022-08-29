package task2618;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class OfficeTest {

    public static void main(String[] args) throws InterruptedException {
        test1();
    }

    public static void test1() throws InterruptedException {
        Office office = new Office();
        new Thread(() -> {
            try {
                office.operating();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"OP-Thread").start();

        new Thread(() -> {
            try {
                office.coding();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"IT-Thread").start();
    }
}

@Slf4j
class Office {
    private final Object OPTable = new Object();
    private final Object ITTable = new Object();

    public void operating() throws InterruptedException {
        synchronized (OPTable) {
            log.debug("运维工作一会儿");
            TimeUnit.SECONDS.sleep(2);
        }
    }

    public void coding() throws InterruptedException {
        synchronized (ITTable) {
            log.debug("IT工作一会儿");
            TimeUnit.SECONDS.sleep(2);
        }
    }
}