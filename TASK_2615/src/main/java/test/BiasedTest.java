package test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

import java.util.ArrayList;
import java.util.Hashtable;

@Slf4j
public class BiasedTest {

    public static void main(String[] args) throws InterruptedException {
        User user = new User();
        user.hashCode();    // 会禁用偏向锁
        log.debug(ClassLayout.parseInstance(user).toPrintable());

        synchronized (user){
            log.debug(ClassLayout.parseInstance(user).toPrintable());
        }

        Thread.sleep(1000);
        log.debug(ClassLayout.parseInstance(user).toPrintable());
    }
}

@Data
@AllArgsConstructor
class User {
}
