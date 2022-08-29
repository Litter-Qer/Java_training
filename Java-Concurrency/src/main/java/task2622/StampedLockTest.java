package task2622;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

@Slf4j
public class StampedLockTest {
    public static void main(String[] args) {
        DataContainerStamped dataContainerStamped = new DataContainerStamped(1);
        new Thread(() -> {
            try {
                dataContainerStamped.read(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1").start();

        new Thread(() -> {
            dataContainerStamped.write(0);
        }, "t2").start();
    }
}

@Slf4j
class DataContainerStamped {
    private int data;
    private final StampedLock lock = new StampedLock();

    public DataContainerStamped(int data) {
        this.data = data;
    }

    public int read(int readTime) throws InterruptedException {
        long stamp = lock.tryOptimisticRead();
        log.debug("read lock {}", stamp);
        TimeUnit.SECONDS.sleep(readTime);
        if (lock.validate(stamp)) {
            log.debug("read finish.. {}", stamp);
            return data;
        }
        log.debug("updating to read lock..{}", stamp);
        try {
            stamp = lock.readLock();
            log.debug("read lock {}", stamp);
            TimeUnit.SECONDS.sleep(readTime);
            log.debug("read finish.. {}", stamp);
            return data;
        } finally {
            log.debug("read unlock {}", stamp);
            lock.unlockRead(stamp);
        }
    }

    public void write(int newData) {
        long stamp = lock.writeLock();
        log.debug("write lock {}", stamp);
        try {
            TimeUnit.SECONDS.sleep(1);
            this.data = newData;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            log.debug("write unlock {}", stamp);
            lock.unlockWrite(stamp);
        }
    }
}
