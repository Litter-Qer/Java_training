package task2620;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Exercise1 {
    volatile boolean initialized = false;

    void init() {
        synchronized (this){
            if (initialized) return;
            doInit();
            initialized = true;
        }
    }

    private void doInit() {
        log.debug("被调用了");
    }
}
