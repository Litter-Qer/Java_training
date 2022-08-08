package test;

import groovyjarjarpicocli.CommandLine;
import helper.Helper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SynchronizedTest {
    static int counter = 0;
    static final Object lock = new Object(); // 锁对象

    public static void main(String[] args) {
//        test1();
//        test2();
        test3();
    }

    private static void test3() {
        Room1 room1 = new Room1();
        Thread t5 = new Thread(()->{
            for (int i = 0; i < 5000; i++) {
                room1.increment();
            }
        },"t5");
        Thread t6 = new Thread(()->{
            for (int i = 0; i < 5000; i++) {
                room1.decrement();
            }
        },"t6");

        t5.start();
        t6.start();
        Helper.join(t5);
        Helper.join(t6);

        log.debug("counter: {}",room1.getCounter());
    }

    public static void test2() {
        Room room = new Room();
        Thread t3 = new Thread(()->{
            for (int i = 0; i < 5000; i++) {
                room.increment();
            }
        },"t3");
        Thread t4 = new Thread(()->{
            for (int i = 0; i < 5000; i++) {
                room.decrement();
            }
        },"t4");

        t3.start();
        t4.start();

        Helper.join(t3);
        Helper.join(t4);

        log.debug("counter: {}",room.getCounter());
    }

    private static void test1() {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                synchronized (lock) {
                    counter++;
                }
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                synchronized (lock) {
                    counter--;
                }
            }
        }, "t2");

        t1.start();
        t2.start();

        log.debug("counter: {}", counter);
    }
}

class Room {
    private int counter = 0;

    public void increment() {
        synchronized (this){
            counter++;
        }
    }

    public void decrement() {
        synchronized (this){
            counter--;
        }
    }

    public int getCounter() {
        synchronized (this){
            return counter;
        }
    }
}

class Room1 {
    private int counter = 0;

    public synchronized void increment() {
        counter++;
    }

    public synchronized void decrement() {
        counter--;
    }

    public synchronized int getCounter() {
        return counter;
    }
}

