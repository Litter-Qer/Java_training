import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AlterTest {
    public static void main(String[] args) {
        WaitLabel waitLabel = new WaitLabel(1,5);

        new Thread(() -> waitLabel.print("1",1,2), "t1").start();
        new Thread(() -> waitLabel.print("2",2,3), "t2").start();
        new Thread(() -> waitLabel.print("3",3,1), "t3").start();
    }
}

@Slf4j
@AllArgsConstructor
class WaitLabel {
    private int flag;
    private int iterations;

    public synchronized void print(String content, int currentFlag, int nextFlag) {
        for (int i = 0; i < iterations; i++) {
            while (this.flag != currentFlag) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    log.debug("等待被打断");
                    e.printStackTrace();
                }
            }
            log.debug("线程 {} 内容：{}",Thread.currentThread().getName(),content);
            this.flag = nextFlag;
            this.notifyAll(); // 全部叫醒
        }
    }
}