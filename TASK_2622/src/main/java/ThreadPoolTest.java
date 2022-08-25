import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class ThreadPoolTest {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(2, new ThreadFactory() {
            private AtomicInteger t = new AtomicInteger(0);

            @Override
            public Thread newThread(@NotNull Runnable r) {
                return new Thread(r, "myPool-" + t.getAndIncrement());
            }
        });
        pool.execute(() -> log.info("1"));
        pool.execute(() -> log.info("2"));
        pool.execute(() -> log.info("3"));

        pool.shutdown();
    }
}
