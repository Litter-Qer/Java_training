import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class PoolTest {

    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool(1, 1000, TimeUnit.MILLISECONDS, 1, (queue, task) -> {
////             死等
//            queue.put(task);
////             超时等待
//            queue.give(task, 1500, TimeUnit.MILLISECONDS);
////             放弃执行
//            log.info("放弃执行");
////             调用者抛出异常
//            throw new RuntimeException("任务加入失败，自动抛出异常 " + task);
////             调用者自己执行任务
//            task.run();
        });

        for (int i = 0; i < 4; i++) {
            int j = i + 1;
            threadPool.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("{}", j);
            });
        }
    }
}

@FunctionalInterface // 函数式接口
interface RefusePolicy<T> {
    void reject(BlockingQueue<T> queue, T task);
}

@Slf4j
class BlockingQueue<T> {
    // 任务队列 生产者消费者模式
    private final Deque<T> queue = new ArrayDeque<>();

    // 需要一把锁
    private final ReentrantLock lock = new ReentrantLock();

    // 生产者
    private final Condition fullWaitSet = lock.newCondition();

    // 消费者
    private final Condition emptyWaitSet = lock.newCondition();

    // 容量
    private final int capacity;

    public BlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    // 获取阻塞
    public T get() {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                try {
                    emptyWaitSet.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t = queue.removeFirst();
            fullWaitSet.signal();
            return t;
        } finally {
            lock.unlock();
        }
    }

    public T get(long timeout, TimeUnit unit) {
        lock.lock();
        try {
            long nanos = unit.toNanos(timeout); // 转换为纳秒
            while (queue.isEmpty()) {
                try {
                    if (nanos <= 0) {
                        return null;
                    }
                    nanos = emptyWaitSet.awaitNanos(nanos); // 返回剩余时间
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t = queue.removeFirst();
            fullWaitSet.signal();
            return t;
        } finally {
            lock.unlock();
        }
    }

    public void put(T element) {
        lock.lock();
        try {
            while (queue.size() == capacity) {
                try {
                    log.info("等待加入队列.........{}", element);
                    fullWaitSet.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.info("加入任务队列.........{}", element);
            queue.addLast(element);
            emptyWaitSet.signal();
        } finally {
            lock.unlock();
        }
    }

    public boolean give(T task, long timeout, TimeUnit timeUnit) {
        lock.lock();
        try {
            long nanos = timeUnit.toNanos(timeout);
            while (queue.size() == capacity) {
                try {
                    log.info("等待加入队列.........{}", task);
                    if (nanos <= 0) return false;
                    nanos = fullWaitSet.awaitNanos(timeout);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.info("加入任务队列.........{}", task);
            queue.addLast(task);
            emptyWaitSet.signal();
            return true;
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        lock.lock();
        try {
            return queue.size();
        } finally {
            lock.unlock();
        }
    }

    public void tryPut(RefusePolicy<T> refusePolicy, T task) {
        lock.lock();
        try {
            if (queue.size() == capacity) {
                refusePolicy.reject(this, task);
            } else {
                log.info("加入任务队列");
                queue.addLast(task);
                emptyWaitSet.signal();
            }
        } finally {
            lock.unlock();
        }
    }
}

@Slf4j
class ThreadPool {
    private final BlockingQueue<Runnable> taskQueue;  // 任务队列
    private final HashSet<Worker> workers = new HashSet<>();  // 线程集合
    private final int coreSize;
    private final long timeout;
    private final TimeUnit timeUnit;

    private final RefusePolicy<Runnable> refusePolicy;

    public ThreadPool(int coreSize, long timeout, TimeUnit timeUnit, int QueueCapacity, RefusePolicy<Runnable> refusePolicy) {
        this.coreSize = coreSize;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        this.taskQueue = new BlockingQueue<>(QueueCapacity);
        this.refusePolicy = refusePolicy;
    }

    public void execute(Runnable task) {
        synchronized (workers) {
            // 任务不超过核心数时，就交给worker对象执行， 超过则加入任务队列
            if (workers.size() < coreSize) {
                Worker worker = new Worker(task);
                log.debug("新增worker: {} {}", worker, task);
                workers.add(worker);
                worker.start();
            } else {
                taskQueue.tryPut(refusePolicy, task);
            }
        }
    }

    class Worker extends Thread {
        private Runnable task;

        public Worker(Runnable task) {
            this.task = task;
        }

        @Override
        public void run() {
            // 执行任务
            while (task != null || (task = taskQueue.get(timeout, timeUnit)) != null) {
                try {
                    log.debug("worker {} task {}", this, task);
                    task.run();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    task = null;
                }
            }
            synchronized (workers) {
                log.debug("worker 被移除 {} 还剩 {}", this, workers);
                workers.remove(this);
            }
        }
    }
}