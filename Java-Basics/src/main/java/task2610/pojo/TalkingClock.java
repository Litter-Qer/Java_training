package task2610.pojo;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class TalkingClock {
    private int interval;
    private boolean beep;

    public TalkingClock() {

    }

    public TalkingClock(int interval) {
        this.interval = interval;
    }

    public TalkingClock(int interval, boolean beep) {
        this(interval);
        this.beep = beep;
    }

    public void start(){
        ActionListener actionListener = new TimerPrinter();
        Timer timer = new Timer(interval, actionListener);
        timer.start();
    }

    /**
     * 内部类 TimerPrinter2
     */
    public class TimerPrinter implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(e);
            if (TalkingClock.this.beep){
                System.out.println("Current time" + new Date());
            }
        }
    }
}


