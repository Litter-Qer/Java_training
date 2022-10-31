import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import redis.clients.jedis.Jedis;
import utils.JedisConnectionFactory;

public class JedisTest {
    private Jedis jedis;

    @Before
    public void setUp() {
        jedis = JedisConnectionFactory.getJedis();
//        jedis.auth("1234");
        jedis.select(0);
    }

    @Test
    public void testString() {
        String result = jedis.set("name", "Xiaohu");
        System.out.println(result);

        String name = jedis.get("name");
        System.out.println(name);
    }

    @Test
    public void testHash() {
        jedis.hset("user:1", "name", "Jack");
        jedis.hset("user:1", "age", "20");
        jedis.hset("user:2", "name", "Jeff");
        jedis.hset("user:2", "age", "31");
    }

    @After
    public void tearDown() {
        if (jedis != null) {
            jedis.close();
        }
    }
}
