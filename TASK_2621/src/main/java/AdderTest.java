import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Slf4j
public class AdderTest {
    public static void main(String[] args) throws InterruptedException {
        demo(() -> new AtomicLong(0), AtomicLong::getAndIncrement);

        demo(LongAdder::new, LongAdder::increment);
    }

    private static <T> void demo(Supplier<T> adderSupplier, Consumer<T> action) throws InterruptedException {
        T adder = adderSupplier.get();
        List<Thread> ts = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            ts.add(new Thread(() -> {
                for (int j = 0; j < 500_000; j++) {
                    action.accept(adder);
                }
            }));
        }

        long start = System.nanoTime();
        ts.forEach(Thread::start);
        for (Thread t : ts) {
            t.join();
        }

        long end = System.nanoTime();
        System.out.println(adder + " cost: " + (end - start) / 1000_000  + " ms");
    }
}
