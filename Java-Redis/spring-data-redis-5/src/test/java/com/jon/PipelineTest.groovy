package com.jon

import com.jon.utils.StringCommands
import io.lettuce.core.LettuceFutures
import io.lettuce.core.RedisClient
import io.lettuce.core.RedisFuture
import io.lettuce.core.api.StatefulRedisConnection
import org.assertj.core.util.Lists
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ClassPathResource
import org.springframework.dao.DataAccessException
import org.springframework.data.redis.connection.RedisConnection
import org.springframework.data.redis.core.RedisCallback
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.core.script.DefaultRedisScript
import spock.lang.Shared
import spock.lang.Specification

import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

@SpringBootTest
class PipelineTest extends Specification {
    @Autowired
    private StringRedisTemplate stringRedisTemplate

    @Shared
    private final RedisClient redisClient = RedisClient.create("redis://1234@192.168.0.139:7005")

    @Shared
    private final StatefulRedisConnection<String, String> lettuceCon = redisClient.connect()

    @Shared
    private final asyncCommands = lettuceCon.async()

    def "simple completableFuture task"() {
        given:
        CompletableFuture<String> future = new CompletableFuture<>()

        System.out.println "Current state: " + future.isDone()

        when:
        future.complete("my value")

        then:
        System.out.println "Current state: " + future.isDone()
        System.out.println "Got value: " + future.get()
    }

    def "lettuce future task"() {
        when:
        RedisFuture<String> future = asyncCommands.get "key"
        def rst = future.get(1, TimeUnit.SECONDS)

        then:
        println rst
    }

    def "lettuce future task handler"() {
        when:
        RedisFuture<String> future = asyncCommands.get "key"
        lettuceCon.setAutoFlushCommands(true)
        future.thenAccept(System.out::println)
        def rst = future.get(1, TimeUnit.SECONDS)

        then:
        println rst

    }

    def "lettuce sync set"() {
        when:
        def syncCommands = lettuceCon.sync()
        lettuceCon.setAutoFlushCommands(true)

        def before = System.currentTimeMillis()

        for (x in (1..1000)) {
            syncCommands.set("a" + x, "v:" + x)
            syncCommands.expire("a" + x, 10)
        }

        def after = System.currentTimeMillis()
        then:
        println(after - before)
    }

    def "lettuce async set"() {
        when:
        def asyncCommands = lettuceCon.async()

        def before = System.currentTimeMillis()

        asyncCommands.setAutoFlushCommands(false)
        List<RedisFuture<?>> futures = Lists.newArrayList();
        for (x in (1..1000)) {
            futures.add(asyncCommands.set("a" + x, "v:" + x))
            futures.add(asyncCommands.expire("a" + x, 10))
        }
        asyncCommands.flushCommands();

        boolean result = LettuceFutures.awaitAll(5, TimeUnit.SECONDS,
                futures.toArray(new RedisFuture[futures.size()]))
        def after = System.currentTimeMillis()

        asyncCommands.set("test", "done")

        then:
        println(after - before)
    }

    def "lettuce batch commands"() {
        when:
        StringCommands stringCommands = lettuceCon.async() as StringCommands
        lettuceCon.setAutoFlushCommands(false)

        def before = System.currentTimeMillis()
        for (x in (1..1000)) {
            stringCommands.set("a" + x, "v:" + x)
            if (x % 50 == 0) {
//                stringCommands.get("a" + x, CommandBatching.queue())
//                stringCommands.get("a" + x, CommandBatching.flush())
                println x + " commands finished"
            }
        }

        def after = System.currentTimeMillis()

        then:
        println(after - before)
    }

    def "test simple pipeline"() {
        when:
        stringRedisTemplate.executePipelined(new RedisCallback<Object>() {
            @Override
            Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.set("test", "done")
                connection.set("name", "jon")
                return null
            }
        })

        then:
        println stringRedisTemplate.opsForValue().get("test")
        println stringRedisTemplate.opsForValue().get("name")
    }

    def "test lua"() {
        DefaultRedisScript UPDATE_SCRIPT = new DefaultRedisScript<>(" redis.call('set', KEYS[1], ARGV[1])\n" +
                "    redis.call('set', KEYS[2], ARGV[2])")
        def keys = new String[2]
        keys[0] = "key1"
        keys[1] = "key2"

        when:
        stringRedisTemplate.opsForValue().set("key1", "value1")
        stringRedisTemplate.opsForValue().set("key2", "value2")

        stringRedisTemplate.execute(UPDATE_SCRIPT, keys.toList(), "value2", "value1")

        then:
        println "Test Done"
    }
}
