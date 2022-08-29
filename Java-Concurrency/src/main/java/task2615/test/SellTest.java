package task2615.test;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

@Slf4j
public class SellTest {
    public static void main(String[] args) throws InterruptedException {
        TicketWindow ticketWindow = new TicketWindow(2000);
        List<Thread> threads = new ArrayList<>();

        List<Integer> sales = new Vector<>();
        for (int i = 0; i < 2000; i++) {
            Thread t = new Thread(() -> {
                int count = ticketWindow.sell(randomAmount());
                sales.add(count);
            });
            threads.add(t);
            t.start();
        }

        for (Thread thread : threads) thread.join();

        log.debug("tickets sold: {}", sales.stream().mapToInt(c -> c).sum());
        log.debug("remaining tickets: {}", ticketWindow.getCount());
    }

    static Random random = new Random();

    public static int randomAmount() {
        return random.nextInt(2) + 1;
    }
}

@Getter
class TicketWindow {
    private int count;

    public TicketWindow(int count) {
        this.count = count;
    }

//    public int sell(int amount) {
//        if (this.count >= amount) {
//            this.count -= amount;
//            return amount;
//        } else return 0;
//    }

    public synchronized int sell(int amount) {
        if (this.count >= amount) {
            this.count -= amount;
            return amount;
        } else return 0;
    }
}
