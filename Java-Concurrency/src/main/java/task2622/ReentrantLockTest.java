package task2622;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j
public class ReentrantLockTest {
    public static void main(String[] args) {
        DataContainer dataContainer = new DataContainer();
        new Thread(dataContainer::read, "t1").start();
        new Thread(dataContainer::read, "t2").start();
    }
}

@Slf4j
class DataContainer {
    private Object data;
    private ReentrantReadWriteLock rw = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock r = rw.readLock();
    private ReentrantReadWriteLock.WriteLock w = rw.writeLock();

    public Object read() {
        log.debug("获取读锁");
        r.lock();
        try {
            log.debug("读取");
            TimeUnit.SECONDS.sleep(1);
            return data;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            log.debug("释放读锁");
            r.unlock();
        }
        return null;
    }

    public void write() {
        log.debug("获取写锁");
        w.lock();
        try {
            log.debug("写入");
        } finally {
            log.debug("释放写锁");
            w.unlock();
        }
    }
}