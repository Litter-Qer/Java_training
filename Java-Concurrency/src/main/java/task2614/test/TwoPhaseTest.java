package task2614.test;

import task2614.pojo.TwoPhaseTermination;


public class TwoPhaseTest {
    public static void main(String[] args) throws InterruptedException {
        TwoPhaseTermination twoPhaseTermination = new TwoPhaseTermination();

        twoPhaseTermination.start();

        Thread.sleep(6000);
        twoPhaseTermination.end();
    }
}