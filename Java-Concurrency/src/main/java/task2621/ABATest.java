package task2621;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

@Slf4j
public class ABATest {
    final static AtomicStampedReference<String> ref = new AtomicStampedReference<>("A", 0);

    public static void main(String[] args) throws InterruptedException {
        String prev = ref.getReference();
        int stamp = ref.getStamp();
        log.debug("Stamp: {} current value: {}", stamp, prev);

        new Thread(() -> {
            int stamp1 = ref.getStamp();
            log.debug("change A->B {} Stamp: {}",
                    ref.compareAndSet(ref.getReference(), "B", stamp1, stamp1 + 1), stamp1);
        }).start();

        TimeUnit.MILLISECONDS.sleep(500);
        new Thread(() -> {
            int stamp1 = ref.getStamp();
            log.debug("change B->A {} Stamp: {}",
                    ref.compareAndSet(ref.getReference(), "A", stamp1, stamp1 + 1), stamp1);
        }).start();

        TimeUnit.SECONDS.sleep(1);
        log.debug("change A -> C {}", ref.compareAndSet(prev, "C", stamp, stamp + 1));
    }
}


