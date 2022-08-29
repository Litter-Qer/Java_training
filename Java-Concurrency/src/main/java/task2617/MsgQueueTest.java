package task2617;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

@Slf4j
public class MsgQueueTest {
    public static void main(String[] args) {
        MessageQueue mq = new MessageQueue(2);

        for (int i = 0; i < 3; i++) {
            int id = i;
            new Thread(() -> {
                mq.put(new Message(id, "value: " + id));
            }, "生产者" + i).start();
        }

        new Thread(() -> {
            while (true){
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message msg = mq.take();
                log.debug("已消费的消息 {}", msg);
            }
        }, "消费者").start();


    }
}

@Slf4j
class MessageQueue {
    private static final LinkedList<Message> msgs = new LinkedList<>();
    private int capacity;

    MessageQueue(int capacity) {
        this.capacity = capacity;
    }

    public Message take() {
        // 检查队列是否为空
        synchronized (msgs) {
            while (msgs.isEmpty()) {
                try {
                    log.debug("队列是空的，无法消费");
                    msgs.wait();
                } catch (InterruptedException e) {
                    log.debug("wait interrupted");
                    e.printStackTrace();
                }
            }
            Message msg = msgs.removeFirst();
            msgs.notifyAll();
            return msg;
        }
    }

    public void put(Message msg) {
        synchronized (msgs) {
            while (msgs.size() == capacity) {    // 检查队列是否已满
                try {
                    log.debug("队列已满，无法加入");
                    msgs.wait();
                } catch (InterruptedException e) {
                    log.debug("wait interrupted");
                    e.printStackTrace();
                }
            }
            log.debug("已生产消息 {}", msg);
            msgs.add(msg);
        }
    }
}

@Getter
@ToString
final class Message {
    private int id;
    private Object value;


    public Message(int id, Object value) {
        this.id = id;
        this.value = value;
    }
}
