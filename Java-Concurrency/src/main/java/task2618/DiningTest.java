package task2618;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class DiningTest {

    public static void main(String[] args) throws InterruptedException {
        test1();
    }

    private static void test1() {
        Chopstick c1 = new Chopstick();
        Chopstick c2 = new Chopstick();
        Chopstick c3 = new Chopstick();
        Chopstick c4 = new Chopstick();
        Chopstick c5 = new Chopstick();

        new Person("小明",c1,c2).start();
        new Person("小王",c2,c3).start();
        new Person("小李",c3,c4).start();
        new Person("小赵",c4,c5).start();
        new Person("小军",c5,c1).start();
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
        while (true){
            synchronized (left){
                synchronized (right){
                    log.debug("{} is eating...", this.getName());
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

class Chopstick {
}