import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
public class ThreadPoolTest4 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        List<Future<String>> futures = pool.invokeAll(Arrays.asList(
                () -> {
                    log.debug("开始");
                    TimeUnit.MILLISECONDS.sleep(1000);
                    return "1";
                },
                () -> {
                    log.debug("开始");
                    TimeUnit.MILLISECONDS.sleep(500);
                    return "2";
                },
                () -> {
                    log.debug("开始");
                    TimeUnit.MILLISECONDS.sleep(2000);
                    return "3";
                }
        ));

        futures.forEach(f->{
            try {
                log.debug("{}", f.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
    }
}
