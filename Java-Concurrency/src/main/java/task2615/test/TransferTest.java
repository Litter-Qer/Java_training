package task2615.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class TransferTest {
    public static void main(String[] args) throws InterruptedException {
        Account a1 = new Account(1000);
        Account a2 = new Account(1000);

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) a1.transfer(a2, randomInt());
        }, "t1");
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) a2.transfer(a1, randomInt());
        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();

//        System.out.println(ClassLayout.parseInstance(a1).toPrintable());
//        System.out.println(VM.current().details());
        log.debug("total {}", a1.getBalance() + a2.getBalance());
    }

    static Random random = new Random();

    public static int randomInt() {
        return random.nextInt(100) + 1;
    }
}

@Data
@AllArgsConstructor
class Account {
    private int balance;

    public void transfer(Account receiver, int amount) {
        synchronized (Account.class) {
            if (this.balance >= amount) {
                this.setBalance(this.getBalance() - amount);
                receiver.setBalance(receiver.getBalance() + amount);
            }
        }
    }
}
