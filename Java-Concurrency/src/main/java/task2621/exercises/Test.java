package task2621.exercises;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Test {
    public static void main(String[] args) {
        Pool pool = new Pool(2);
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                Connection conn = pool.getConnection();
                try {
                    TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                pool.free(conn);
            }).start();
        }
    }
}


