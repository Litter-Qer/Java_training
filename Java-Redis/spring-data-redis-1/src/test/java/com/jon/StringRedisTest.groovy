package com.jon

import com.fasterxml.jackson.databind.ObjectMapper
import com.jon.pojo.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.StringRedisTemplate
import spock.lang.Shared
import spock.lang.Specification

@SpringBootTest
class StringRedisTest extends Specification {
    @Autowired
    private StringRedisTemplate stringRedisTemplate

    @Shared
    public final ObjectMapper mapper = new ObjectMapper();

    def "insert an user string"() {
        given:
        def user = new User("LitterQer", 12)


        when:
        stringRedisTemplate.opsForValue().set("user:4", mapper.writeValueAsString(user))
        def userStr = stringRedisTemplate.opsForValue().get("user:4")

        then:
        println mapper.readValue(userStr, User.class)
    }
}
