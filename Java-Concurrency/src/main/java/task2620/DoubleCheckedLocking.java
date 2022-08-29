package task2620;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DoubleCheckedLocking {
    private DoubleCheckedLocking() { }
    private volatile static DoubleCheckedLocking INSTANCE = null;
    public static DoubleCheckedLocking getInstance() {
        if (INSTANCE == null) {
            synchronized (DoubleCheckedLocking.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DoubleCheckedLocking();
                }
            }
        }
        return INSTANCE;
    }
}
