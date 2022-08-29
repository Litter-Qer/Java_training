package task2614.test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RunStartTest {
    public static void main(String[] args) {
//        runTest();
        startTest();
//        runTest2();
    }

    public static void runTest2() {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                log.debug("t1 运行中......");
            }
        };
        Thread t2 = new Thread("t2") {
            @Override
            public void run() {
                log.debug("t2 运行中......");
            }
        };

        t1.start();
        t2.start();
    }

    public static void runTest() {
        log.error("run启动");
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                log.error(getState().name());
                log.error("t1 运行中......");
            }
        };
        log.error(t1.getState().name());
        t1.run();
        log.error("t1 结束运行");
        log.error("-----------");
    }

    public static void startTest() {
        log.error("start启动方法");
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                log.error(getState().name());
                log.error("t1 运行中.......");
                log.error(getState().name());
            }
        };

        log.error(t1.getState().name());
        t1.start();
        log.error(t1.getState().name());
        log.error("t1 结束运行....");
    }
}

