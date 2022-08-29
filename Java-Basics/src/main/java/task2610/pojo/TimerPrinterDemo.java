package task2610.pojo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class TimerPrinterDemo implements ActionListener {
    private boolean beep;

    public TimerPrinterDemo(boolean beep) {
        this.beep = beep;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e);
        if (beep){
            System.out.println("Current time" + new Date());
        }
    }
}
