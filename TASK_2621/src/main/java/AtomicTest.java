import java.util.concurrent.atomic.AtomicInteger;

public class AtomicTest {
    public static void main(String[] args) {
        AtomicInteger i = new AtomicInteger(0);

        System.out.println(i.incrementAndGet());
        System.out.println(i.getAndIncrement());
        System.out.println(i.get());

        i.updateAndGet(x -> x * 10);
        System.out.println(i);

    }
}
