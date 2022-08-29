package task2621;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

@Slf4j
public class DataFormatTest {
    public static void main(String[] args) {
//        test1();
        test2();
    }

    private static void test2() {
        DateTimeFormatter stf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (int i = 0; i < 10; i++)
            new Thread(() -> log.debug("{}", stf.parse("1951-04-21"))).start();
    }

    public static void test1() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    log.debug("{}", sdf.parse("1951-04-21"));
                } catch (ParseException e) {
                    log.error("{}", e);
                }
            }).start();
        }
    }
}


