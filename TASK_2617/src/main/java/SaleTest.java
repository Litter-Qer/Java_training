import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SaleTest {
    static final int value = 100000;

    public static void main(String[] args) throws InterruptedException {
//        test1();
//        test2();
        test3();
    }

    public static int randomInt() {
        return ThreadLocalRandom.current().nextInt(100) + 1;
    }

    public static void test1() throws InterruptedException {
        Account a1 = new Account(value);
        Account a2 = new Account(value);
        Account a3 = new Account(value);

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < value / 4; i++) {
                LockTransfer1.transfer(a1, a2, 1);
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < value / 4; i++) {
                LockTransfer1.transfer(a1, a3, 1);
            }
        }, "t2");

//        Thread t2 = new Thread(() -> {
//            for (int i = 0; i < 100000; i++) {
//                LockTransfer1.transfer(a2, a1, randomInt());
//                LockTransfer1.transfer(a3, a1, randomInt());
//            }
//        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("total " + a1.getBalance() + " | " + a2.getBalance() + " | " + a3.getBalance());
    }

    public static void test2() throws InterruptedException {
        Account a3 = new Account(value);
        Account a4 = new Account(value);

        Thread t1 = new Thread(() -> {
//            for (int i = 0; i < value; i++) LockTransfer2.transfer(a3, a4, randomInt());
            for (int i = 0; i < value; i++) LockTransfer2.transfer(a3, a4, 1);
        }, "t1");
        Thread t2 = new Thread(() -> {
//            for (int i = 0; i < value; i++) LockTransfer2.transfer(a4, a3, randomInt());
            for (int i = 0; i < value; i++) LockTransfer2.transfer(a4, a3, 1);
        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("total " + a3.getBalance() + " | " + a4.getBalance());
    }

    public static void test3() throws InterruptedException {
        Account a1 = new Account(value);
        Account a2 = new Account(value);
        Account a3 = new Account(value);

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < value / 4; i++) {
                LockTransfer2.transfer(a1, a2, 1);
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < value / 4; i++) {
                LockTransfer2.transfer(a2, a1, 1);
            }
        }, "t2");

        Thread t3 = new Thread(() -> {
            for (int i = 0; i < value / 4; i++) {
                LockTransfer2.transfer(a3, a1, 1);
            }
        }, "t3");

        System.out.println(a1.toString() + a2.toString() + a3.toString());
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();

        System.out.println("total " + a1.getBalance() + " | " + a2.getBalance() + " | " + a3.getBalance());
    }
}

class Account {
    private int balance;

    public Account(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}

class LockTransfer1 {
    private static final ConcurrentHashMap<String, Object> lockMap = new ConcurrentHashMap<>();

    public static void transfer(Account sender, Account receiver, int amount) {
        String key = getKey(sender, receiver);
        Object lock = lockMap.computeIfAbsent(key, k -> new Object());
        synchronized (lock) {
            if (sender.getBalance() >= amount) {
                sender.setBalance(sender.getBalance() - amount);
                receiver.setBalance(receiver.getBalance() + amount);
            }
        }
    }

    private static String getKey(Account sender, Account receiver) {
        return List.of(sender, receiver)
                .stream().map(Objects::toString)
                .sorted().collect(Collectors.joining());
    }
}

class LockTransfer2 {
    public static void transfer(Account sender, Account receiver, int amount) {
        Account a, b;

        if (sender.toString().compareTo(receiver.toString()) >= 0) {
            a = sender;
            b = receiver;
        } else {
            a = receiver;
            b = sender;
        }

        synchronized (a) {
            synchronized (b) {
                if (sender.getBalance() >= amount) {
                    sender.setBalance(sender.getBalance() - amount);
                    receiver.setBalance(receiver.getBalance() + amount);
                }
            }
        }
    }
}

class LockTransfer3 {
    public static void transfer(Account sender, Account receiver, int amount) {
        synchronized (sender){
            synchronized (receiver){
                if (sender.getBalance() >= amount) {
                    sender.setBalance(sender.getBalance() - amount);
                    receiver.setBalance(receiver.getBalance() + amount);
                }
            }
        }
    }
}
