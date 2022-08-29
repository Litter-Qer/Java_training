package task2621;

import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class AccountTest {
    public static void main(String[] args) {
//        UnsafeAccount unsafeAccount = new UnsafeAccount(10_000);
//        Account.demo(unsafeAccount);
//
//        SafeAccount safeAccount = new SafeAccount(10_000);
//        Account.demo(safeAccount);

        CASAccount casAccount = new CASAccount(10_000);
        Account.demo(casAccount);
    }
}

@Slf4j
class UnsafeAccount implements Account {
    private Integer balance;

    public UnsafeAccount(Integer balance) {
        this.balance = balance;
    }

    @Override
    public Integer getBalance() {
        return balance;
    }

    @Override
    public void withdraw(Integer amount) {
        if (balance >= amount) balance -= amount;
    }
}

@Slf4j
class SafeAccount implements Account {
    private Integer balance;

    public SafeAccount(Integer balance) {
        this.balance = balance;
    }

    @Override
    public synchronized Integer getBalance() {
        return balance;
    }

    @Override
    public synchronized void withdraw(Integer amount) {
        if (balance >= amount) this.balance -= amount;
    }
}

@Slf4j
class CASAccount implements Account {
    private AtomicInteger balance;

    public CASAccount(Integer balance) {
        this.balance = new AtomicInteger(balance);
    }

    @Override
    public Integer getBalance() {
        return balance.get();
    }

    @Override
    public void withdraw(Integer amount) {
        int next = balance.get();
        while (true) {
            int before = balance.get();
            if (before >= amount) next = before - amount;
            if (balance.compareAndSet(before, next))
                break;
//            else
//                log.debug("修改失败");

        }
    }
}


interface Account {
    Integer getBalance();

    void withdraw(Integer amount);

    static void demo(Account account) {
        List<Thread> ts = new ArrayList<>();
        for (int i = 0; i < 1000; i++)
            ts.add(new Thread(() -> account.withdraw(10)));
        long start = System.nanoTime();
        ts.forEach(Thread::start);
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long end = System.nanoTime();
        System.out.println(MessageFormat.format("account balance: {0} time consumed: {1} ms",
                account.getBalance(), (end - start) / 1000_000));
    }
}
