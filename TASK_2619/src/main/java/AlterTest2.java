import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class AlterTest2 {

    private static final AwaitSignal awaitSignal = new AwaitSignal(5);
    private static final Condition condA = awaitSignal.newCondition();
    private static final Condition condB = awaitSignal.newCondition();
    private static final Condition condC = awaitSignal.newCondition();

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            awaitSignal.print("1", condA, getNextCond(condA));
        }, "t1").start();

        new Thread(() -> {
            awaitSignal.print("2", condB, getNextCond(condB));
        }, "t2").start();

        new Thread(() -> {
            awaitSignal.print("3", condC, getNextCond(condC));
        }, "t3").start();

        TimeUnit.SECONDS.sleep(1);
        awaitSignal.lock();
        try {
            log.debug("start printing");
            condA.signal();
        } finally {
            awaitSignal.unlock();
        }
    }

    public static Condition getNextCond(Condition current) {
        if (current == condA) return condB;
        if (current == condB) return condC;
        if (current == condC) return condA;
        return null;
    }
}


@Slf4j
class AwaitSignal extends ReentrantLock {
    private int iterations;

    public AwaitSignal(int iterations) {
        this.iterations = iterations;
    }

    public void print(String context, Condition current, Condition next) {
        for (int i = 0; i < this.iterations; i++) {
            lock();
            try {
                current.await();
                log.debug("线程 {} 内容是 {}", Thread.currentThread(), context);
                next.signal();
            } catch (NullPointerException e) {
                log.debug("条件变量不存在 当前条件变量为 {}", current);
                e.printStackTrace();
            } catch (InterruptedException e) {
                log.debug("等待被打断");
                e.printStackTrace();
            } finally {
                unlock();
            }
        }
    }
}

