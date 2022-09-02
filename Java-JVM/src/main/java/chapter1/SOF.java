package chapter1;

import java.util.ArrayList;
import java.util.List;

public class SOF {
    private int stackLength = 1;

    public void stackLeak() {
        if (stackLength == 1000) {
            System.out.println("good");
            System.gc();
        }
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) {
        SOF sof = new SOF();
        try {
            sof.stackLeak();
        } catch (Throwable e) {
            System.out.println("stack length:" + sof.stackLength);
            throw e;
        }
    }
}

