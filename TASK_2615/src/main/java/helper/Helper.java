package helper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Helper {
    /**
     * 当前线程直接休眠，不需要在多写一次try catch块
     * @param i 休眠时间 (ms)
     */
    public static void sleep(long i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            log.debug("睡眠被打断，抛出异常");
        }
    }


    /**
     * 当前线程直接join，无需再写catch 块
     * @param t 需要等待的线程
     */
    public static void join(Thread t) {
        try {
            t.join();
        } catch (InterruptedException e) {
            log.debug("join被打断");
        }
    }
}
