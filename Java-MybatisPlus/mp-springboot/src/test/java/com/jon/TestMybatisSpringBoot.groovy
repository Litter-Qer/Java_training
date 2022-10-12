package com.jon

import com.jon.mapper.UserMapper
import com.jon.pojo.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class TestMybatisSpringBoot extends Specification {

    @Autowired
    UserMapper userMapper;

    def "select all users from the table"() {
        given:
        List<User> users = userMapper.selectList(null)

        when:
        println("Data Granted")

        then:
        !users.empty
        users.forEach(System.out::println)
    }
}
