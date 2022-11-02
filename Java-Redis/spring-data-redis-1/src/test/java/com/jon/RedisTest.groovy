package com.jon

import com.jon.pojo.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.RedisTemplate
import spock.lang.Specification

@SpringBootTest
class RedisTest extends Specification {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    def "write to a string"() {
        when:
        redisTemplate.opsForValue().set("name", "Jon")
        def name = redisTemplate.opsForValue().get("name")

        then:
        println "name: " + name
    }

    def "write to a hash"() {
        when:
        redisTemplate.opsForHash().put("user:1", "name", "Jon")
        redisTemplate.opsForHash().put("user:1", "age", "8")
        redisTemplate.opsForHash().put("user:2", "name", "Ryan")
        redisTemplate.opsForHash().put("user:2", "age", "28")

        def user1 = redisTemplate.opsForHash().values("user:1")
        def user2 = redisTemplate.opsForHash().values("user:2")

        then:
        println user1
        println user2
    }

    def "save an object"() {
        when:
        redisTemplate.opsForValue().set("user:3", new User("XiaoHu", 23))
        def user = redisTemplate.opsForValue().get("user:3")

        then:
        println user
    }
}
