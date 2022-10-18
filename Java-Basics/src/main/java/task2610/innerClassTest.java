package task2610;

import task2610.pojo.OuterClass;
import task2610.pojo.OuterClass2;
import task2610.pojo.TalkingClock;

public class innerClassTest {
//    final static boolean isBeep = true;

    public static void main(String[] args) {
        Demo demo = new Demo() {
            @Override
            public void run() {
                System.out.println("这是一个匿名内部类的demo");
            }
        };
    }

    public static void InnerTest2()
    {
        OuterClass2 out2 = new OuterClass2();
        OuterClass2.InnerClass2 in2 = out2.new InnerClass2();
        OuterClass2.InnerClass2.show();
    }

    public void InnerTest1()
    {
        OuterClass out = new OuterClass();
        OuterClass.InnerClass in = out.new InnerClass();
        OuterClass.InnerClass in2 = out.createInner();
    }

    public void beepTest(){
        TalkingClock tck = new TalkingClock(1000, true);
        TalkingClock.TimerPrinter timerPrinter = tck.new TimerPrinter();
    }

}

abstract class Demo {
    public abstract void run();
}