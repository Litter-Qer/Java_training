package com.jon

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate
import spock.lang.Specification

@SpringBootTest
class SpringApplicationTest extends Specification {

    @Autowired
    private RedisTemplate redisTemplate

    @Autowired
    private StringRedisTemplate stringRedisTemplate

    def "set name value"() {
        given:
        def ops = redisTemplate.opsForValue()

        when:
        ops.set("age", 25)
        println "done"

        then:
        println ops.get("age")
    }

    def "get name value"() {
        given:
        def ops = redisTemplate.opsForValue()

        when:
        def value = ops.get("age")

        then:
        println value
    }

    def "set hset name value"() {
        given:
        def ops = redisTemplate.opsForHash()

        when:
        ops.put("info", "age", "25")

        then:
        println "successfully added"
    }

    def "get hset name value"() {
        given:
        def ops = redisTemplate.opsForHash()

        when:
        def value = ops.get("info", "age")

        then:
        println value
    }

    def "get name"() {
        given:
        def ops = stringRedisTemplate.opsForValue()

        when:
        def value = ops.get("name")

        then:
        println value
        value == "jon"
    }
}
