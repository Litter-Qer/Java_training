import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class AtomicTest {
    volatile static boolean run = true;
//    static boolean run = true;


    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            while (run) {
                // nothing here
//                log.debug("");
                System.out.println();
            }
        });

        t.start();
        TimeUnit.SECONDS.sleep(1);
        log.debug("停止 t");
        run = false;
    }
}
