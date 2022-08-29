package task2614.pojo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TwoPhaseTermination {
    private Thread monitor;

    /**
     * 启动监控线程
     */
    public void start() {
        monitor = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    log.debug("被打断了");
                    break;
                }
                try {
                    Thread.sleep(1000); // 释放CPU占用
                    log.debug("正常运行中...");
                } catch (InterruptedException e) {
                    log.error("休眠中被打断，会重置打断标记,重新设置打断标记");
                    Thread.currentThread().interrupt();
                }
            }
        }, "monitor");
        monitor.start();
    }

    /**
     * 停止监控线程
     */
    public void end() {
        monitor.interrupt();
    }
}
