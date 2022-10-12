package com.jon


import com.jon.mapper.UserMapper
import com.jon.pojo.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class TestMybatisAR extends Specification {

    def "select by Id"() {
        given:
        def user = new User()
        user.setId(2L)

        when:
        def user1 = user.selectById()

        then:
        println user1
    }

    def "insert by Id"() {
        given:
        def user = new User()
        user.setUserName("Good")
        user.setPassword("951753")
        user.setAge("85")
        user.setUserEmail("gugu@gmail.com")
        user.setName("Xiaohu")

        when:
        def user1 = user.insert()

        then:
        println user1
    }
}
