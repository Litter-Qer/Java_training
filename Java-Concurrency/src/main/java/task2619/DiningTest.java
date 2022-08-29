package task2619;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class DiningTest {

    public static void main(String[] args) throws InterruptedException {
        test1();
    }

    private static void test1() {
        Chopstick c1 = new Chopstick("c1");
        Chopstick c2 = new Chopstick("c2");
        Chopstick c3 = new Chopstick("c3");
        Chopstick c4 = new Chopstick("c4");
        Chopstick c5 = new Chopstick("c5");

        new Person("小明", c1, c2).start();
        new Person("小王", c2, c3).start();
        new Person("小李", c3, c4).start();
        new Person("小赵", c4, c5).start();
        new Person("小军", c5, c1).start();
    }
}

@Slf4j
class Person extends Thread {
    private final Chopstick left;
    private final Chopstick right;

    public Person(String name, Chopstick left, Chopstick right) {
        super(name);
        this.left = left;
        this.right = right;
    }

    @Override
    public void run() {
        while (true) {
            if (left.tryLock()) {
                try {
                    if (right.tryLock()) {
                        try {
                            log.debug("开吃");
                            TimeUnit.MILLISECONDS.sleep(40);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            right.unlock();
                        }
                    }
                } finally {
                    left.unlock();
                }
            }
        }
    }
}

class Chopstick extends ReentrantLock {
    String name;

    public Chopstick(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}