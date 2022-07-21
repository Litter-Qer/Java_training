import org.junit.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

public class SimpleStackWalkerTest {
    private static final Logger logger = LoggerFactory.getLogger(SimpleStackWalkerTest.class);

    public static void main(String[] args) {
//        test();
//        test();
        test2();
        test2();
    }

    private static void test() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; ++i) {
            try {
                throw new RuntimeException("" + Math.random());
            } catch (Exception e) {
                StackTraceElement[] stackTrace = e.getStackTrace();
            }
        }
        System.out.println("time: " + (System.currentTimeMillis() - start));
    }

    @Test
    private static void test2() {
        long start = System.currentTimeMillis();
        for (int i=0; i<100000; ++i) {
            try {
                throw new RuntimeException("" + Math.random());
            } catch (Exception e) {
                logger.error(e,()->null);
            }
        }
        System.out.println("time: " + (System.currentTimeMillis() - start));
    }
}
