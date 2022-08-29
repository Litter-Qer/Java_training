package task2620;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class TwoPhaseTest {
    private Thread monitor;
    private volatile boolean isStopped = false;

    private boolean isStarted = false;

    public static void main(String[] args) throws InterruptedException {
        TwoPhaseTest twoPhaseTest = new TwoPhaseTest();
        twoPhaseTest.start();
        twoPhaseTest.start();
        twoPhaseTest.start();
        twoPhaseTest.stop();
    }

    public void start() {
        synchronized (this){
            if (isStarted) {
                log.debug("别的线程已经启动过了");
                return;
            }
            isStarted = true;
            log.debug("启动线程");
            monitor = new Thread(() -> {
                while (true) {
                    if (isStopped) {
                        log.debug("优雅停止");
                        break;
                    }
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "monitor");
            monitor.start();
        }
    }

    public void stop() {
        isStopped = true;
    }
}
