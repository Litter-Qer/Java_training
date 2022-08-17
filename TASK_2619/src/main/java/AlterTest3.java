import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

@Slf4j
public class AlterTest3 {
    private static final ParkUnpark parkUnpark = new ParkUnpark(5);
    static Thread t1, t2, t3;

    public static void main(String[] args) throws InterruptedException {
        t1 = new Thread(() -> {
            parkUnpark.print("1", t2);
        }, "t1");

        t2 = new Thread(() -> {
            parkUnpark.print("2", t3);
        }, "t2");

        t3 = new Thread(() -> {
            parkUnpark.print("3", t1);
        }, "t3");

        t1.start();
        t2.start();
        t3.start();

        LockSupport.unpark(t1);
    }
}


class ParkUnpark {
    private int iterations;

    public ParkUnpark(int iterations) {
        this.iterations = iterations;
    }

    public void print(String content, Thread next) {
        for (int i = 0; i < iterations; i++) {
            LockSupport.park();
            System.out.print(content);
            LockSupport.unpark(next);
        }
    }
}


