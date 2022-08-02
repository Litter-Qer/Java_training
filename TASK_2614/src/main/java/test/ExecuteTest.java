package test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExecuteTest {
    public static void main(String[] args) {
        for(;;){
            exeTest1();
        }
    }

    public static void exeTest1() {
        new Thread(()->{
            log.debug("t1 running");
        },"t1").start();
        new Thread(()->{
            log.debug("t2 running");
        },"t2").start();
    }
}
