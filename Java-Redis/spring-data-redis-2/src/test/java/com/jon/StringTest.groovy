package com.jon

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.RedisTemplate
import spock.lang.Shared
import spock.lang.Specification

import java.time.Duration

@SpringBootTest
class StringTest extends Specification {
    @Autowired
    private RedisTemplate redisTemplate

    @Shared
    def ids = new ArrayList()

    def setupSpec() {
        for (i in (1..20)) ids.add(i)
    }

    def cleanup() {
        println "done"
    }

    def "Single-Thread set and get -- [#id]"() {
        when:
        redisTemplate.opsForValue().set("id:" + id, id)

        then:
        println redisTemplate.opsForValue().get("id:" + id)

        where:
        id << ids
    }

    def "Multi-Thread set and get -- [#id]"() {
        given:
        def t1 = new Thread(() -> redisTemplate.opsForValue().set("id:" + id, "t1:" + id), "t1")
        def t2 = new Thread(() -> redisTemplate.opsForValue().set("id:" + id, "t2:" + id), "t2")
        def t3 = new Thread(() -> redisTemplate.opsForValue().set("id:" + id, "t3:" + id), "t3")
        def t4 = new Thread(() -> redisTemplate.opsForValue().set("id:" + id, "t4:" + id), "t4")

        when:
        t1.start()
        t2.start()
        t3.start()
        t4.start()

        then:
        println redisTemplate.opsForValue().get("id:" + id)

        where:
        id << ids
    }

    def "Multi-Thread setnx -- [#id]"() {
        given:
        def t1 = new Thread(() -> redisTemplate.opsForValue().setIfAbsent("id:" + id, "t1:" + id), "t1")
        def t2 = new Thread(() -> redisTemplate.opsForValue().setIfAbsent("id:" + id, "t2:" + id), "t2")
        def t3 = new Thread(() -> redisTemplate.opsForValue().setIfAbsent("id:" + id, "t3:" + id), "t3")
        def t4 = new Thread(() -> redisTemplate.opsForValue().setIfAbsent("id:" + id, "t4:" + id), "t4")

        when:
        t1.start()
        t2.start()
        t3.start()
        t4.start()

        then:
        println redisTemplate.opsForValue().get("id:" + id)

        where:
        id << ids
    }

    def "Multi-Thread setnx timeout -- [#id]"() {
        given:
        def t1 = new Thread(() -> redisTemplate.opsForValue().setIfAbsent("id:" + id, "t1:" + id, Duration.ofSeconds(30)), "t1")
        def t2 = new Thread(() -> redisTemplate.opsForValue().setIfAbsent("id:" + id, "t2:" + id, Duration.ofSeconds(30)), "t2")
        def t3 = new Thread(() -> redisTemplate.opsForValue().setIfAbsent("id:" + id, "t3:" + id, Duration.ofSeconds(30)), "t3")

        when:
        t1.start()
        t2.start()
        t3.start()

        then:
        println redisTemplate.opsForValue().get("id:" + id)

        where:
        id << ids
    }

    def "Multi-Thread setnx timeout lock -- [#id]"() {
        given:
        def t1 = new Thread(() -> {
            if (redisTemplate.opsForValue().setIfAbsent("setLock", "lock", Duration.ofSeconds(3))) {
                redisTemplate.opsForValue().set("id:" + id, "t1:" + id, Duration.ofSeconds(30))
                redisTemplate.opsForValue().getAndDelete("setLock")
            }
        }, "t1")

        def t2 = new Thread(() -> {
            if (redisTemplate.opsForValue().setIfAbsent("setLock", "lock", Duration.ofSeconds(3))) {
                redisTemplate.opsForValue().set("id:" + id, "t2:" + id, Duration.ofSeconds(30))
                redisTemplate.opsForValue().getAndDelete("setLock")
            }
        }, "t2")

        def t3 = new Thread(() -> {
            if (redisTemplate.opsForValue().setIfAbsent("setLock", "lock", Duration.ofSeconds(3))) {
                redisTemplate.opsForValue().set("id:" + id, "t3:" + id, Duration.ofSeconds(30))
                redisTemplate.opsForValue().getAndDelete("setLock")
            }
        }, "t3")

        when:
        t1.start()
        t2.start()
        t3.start()

        then:
        println redisTemplate.opsForValue().get("id:" + id)

        where:
        id << ids
    }

    def "Hash set get timeout -- [#id]"() {
        when:
        redisTemplate.opsForHash().put("user:" + id, id.toString(), "main:" + id)
        redisTemplate.expire("user:" + id, Duration.ofSeconds(10))

        then:
        println ids

        where:
        id << ids
    }

    def "Zset top 10"() {
        given:
        for (x in 1..rounds) {
            if (!redisTemplate.opsForZSet().addIfAbsent("user", "user:" + user, 1)) {
                redisTemplate.opsForZSet().incrementScore("user", "user:" + user, diff)
            }
        }

        when:
        def users = redisTemplate.opsForZSet().reverseRangeWithScores("user", 0, 9)

        then:
        users.forEach(System.out::println)

        where:
        user << ids
        diff = new Random().nextInt(2, 10)
        rounds = new Random().nextInt(2, 7)
    }

    def "delete all records"() {
        given:
        redisTemplate.opsForZSet().remove("user", "user:" + user)

        when:
        println "deleted"

        then:
        println "all done"

        where:
        user << ids
    }

    def "set key with {}"() {
        when:
        redisTemplate.opsForValue().setIfAbsent("{a}","a:string")

        then:
        println redisTemplate.opsForValue().get("{a}")
    }
}
