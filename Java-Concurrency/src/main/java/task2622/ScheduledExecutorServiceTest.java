package task2622;

import lombok.extern.slf4j.Slf4j;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ScheduledExecutorServiceTest {
    public static void main(String[] args) {
        // 当前时间
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);

        // 修改到具体日期
        LocalDateTime time = now.withHour(18).withMinute(0).withSecond(0).withNano(0).with(DayOfWeek.THURSDAY);
        if (now.compareTo(time) > 0) time = time.plusWeeks(1);

        long period = 1000 * 60 * 60 * 24 * 7;
        long initialDelay = Duration.between(now, time).toMillis();

        ScheduledExecutorService pool = Executors.newScheduledThreadPool(2);
//        pool.schedule(() -> {
//            log.debug("111");
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }, 1, TimeUnit.SECONDS);
//        pool.schedule(() -> log.debug("222"), 1, TimeUnit.SECONDS);
        pool.scheduleAtFixedRate(() -> log.debug("3333"), initialDelay, period, TimeUnit.SECONDS);

    }
}
