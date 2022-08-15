import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.cassandra.db.SimpleBuilders;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

@Slf4j
public class GuardedObjectTest {

    public static void main(String[] args) throws InterruptedException {
//        test1();
        test2();
    }

    public static void test1() {
        GuardedObject guardedObject = new GuardedObject();

        new Thread(() -> {
            log.debug("thread 1 waiting for the result of thread 2");
            Object a = guardedObject.get(2000); // 加入等待时间
            if (a != null) log.debug(a.toString());
            else log.debug("时间到，直接结束等待");
        }, "t1").start();

        new Thread(() -> {
            log.debug("thread 2 doing some jobs here");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Object obj = new Object();
            guardedObject.complete(obj);
        }, "t2").start();
    }

    public static void test2() throws InterruptedException {
        for (int i = 0; i < 4; i++) {
            new Resident().start();
        }
        TimeUnit.SECONDS.sleep(2);  // 开始送快递
        for (Integer id : Mailboxes.getIds()) {
            new Postman(id,"good day -" + id).start();
        }
    }
}

class Mailboxes {
    private static final Map<Integer, GuardedObject> mailBoxes = new Hashtable<>(); // 线程安全的
    private static int id = 0;

    /**
     * 不同线程同时获取id，保证线程安全
     *
     * @return 门牌号id
     */
    private static synchronized int generateId() {
        id++;
        return id;
    }

    /**
     * 创建一个保护性对象 自增id
     *
     * @return 创建的保护性对象
     */
    public static GuardedObject createGuardedObject() {
        GuardedObject guardedObject = new GuardedObject(generateId());
        mailBoxes.put(guardedObject.getId(), guardedObject);
        return guardedObject;
    }

    /**
     * 获得所有的id
     *
     * @return 所有id
     */
    public static Set<Integer> getIds() {
        return mailBoxes.keySet();
    }

    /**
     * 获取对象
     *
     * @return 保护性对象
     */
    public static GuardedObject getGuardedObject(int id) {
        return mailBoxes.remove(id);
    }
}

@Getter
@Setter
class GuardedObject {
    private Object response;
    private int id;

    public GuardedObject() {
    }

    public GuardedObject(int id) {
        this.id = id;
    }

    // obtain results 加入超时
    public Object get(long timeout) {
        synchronized (this) {
            long startTime = System.currentTimeMillis();
            long timePass = 0;
            while (response == null) {
                if (timePass >= timeout) break;
                try {
                    this.wait(timeout - timePass);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                timePass = System.currentTimeMillis() - startTime;
            }
            return response;
        }
    }

    // 结果已经产生了
    public void complete(Object response) {
        synchronized (this) {
            this.response = response;
            this.notifyAll(); // 确保会被notify
        }
    }
}

@Slf4j
class Resident extends Thread {
    @Override
    public void run() {
        // 收快递
        GuardedObject guardedObject = Mailboxes.createGuardedObject();
        log.debug("{} 在等快递", guardedObject.getId());
        guardedObject.get(3000);
        log.debug("{} 收到，快递 {}", guardedObject.getId(), guardedObject);
    }
}

@Slf4j
@AllArgsConstructor
class Postman extends Thread {
    private int mailId;
    private String mail;

    @Override
    public void run() {
        // 收快递
        GuardedObject guardedObject = Mailboxes.getGuardedObject(mailId);
        log.debug("快递员: 送 {} 的快递, 里面是 {}", mailId, guardedObject);
        guardedObject.complete(guardedObject);
        log.debug("快递员: 送 {} 的快递, 内容是 {}", mailId, mail);
    }
}