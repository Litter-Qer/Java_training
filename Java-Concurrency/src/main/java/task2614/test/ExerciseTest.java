package task2614.test;
import lombok.extern.slf4j.Slf4j;
import pojo.Helper;

import java.util.concurrent.ExecutionException;

@Slf4j
public class ExerciseTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        test1();
    }
    
    public static void test1(){
        Thread t1 = new Thread(()->{
            log.debug("洗水壶");
            Helper.sleep(1000); // helper方法，无需再写try catch块
            log.debug("烧水");
            Helper.sleep(5000);
        },"烧水线程");

        Thread t2 = new Thread(()->{
            log.debug("洗茶杯");
            Helper.sleep(2000);
            log.debug("洗茶壶");
            Helper.sleep(1000);
            log.debug("拿茶叶");
            Helper.sleep(1000);
            Helper.join(t1);    // 等待水烧开
        },"清洗线程");

        t1.start();
        t2.start();

        Helper.join(t1);
        Helper.join(t2);
        log.debug("茶来了");
    }


}

