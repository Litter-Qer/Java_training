import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class ThreadPoolTest3 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        Future<String> future = pool.submit(() -> {
            log.info("开始了");
            TimeUnit.MILLISECONDS.sleep(50);
            return "成功执行";
        });

        TimeUnit.SECONDS.sleep(1);
        log.info("Future {}", future.get());
    }
}
