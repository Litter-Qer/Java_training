package test;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

@Slf4j
public class LocalVarTest {
    static ArrayList<String> strings = new ArrayList<>();
    static Hashtable<String,String> myStringTable = new Hashtable<>();

    public static void main(String[] args) {
//        test1();
//        test2();
//        test3();
        test4();
    }

    private static void test4() {
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                if (myStringTable.get("key") == null){
                    myStringTable.put("key","a");
                }
            }, "Thread" + i + 1).start();
        }
    }

    private static void test3() {
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    myStringTable.put("1","a");
                    myStringTable.remove("1");
                }
            }, "Thread" + i + 1).start();
        }
    }

    private static void test2() {
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    add();
                    remove();
                }
            }, "Thread" + i + 1).start();
        }
    }

    public static void test1() {
        int i = 10;
        i++;
    }

    private static void add() {
        strings.add("1");
    }

    private static void remove() {
        strings.remove(0);
    }

}
