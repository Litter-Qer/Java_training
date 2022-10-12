package com.jon

import com.jon.mapper.UserMapper
import com.jon.pojo.User
import org.apache.ibatis.io.Resources
import org.apache.ibatis.session.SqlSession
import org.apache.ibatis.session.SqlSessionFactory
import org.apache.ibatis.session.SqlSessionFactoryBuilder
import spock.lang.Specification

class TestMybatis extends Specification {
    def "find all users"() {
        given:
        String config = "mybatis-config.xml"
        InputStream inputStream = Resources.getResourceAsStream(config)
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream)
        SqlSession session = sqlSessionFactory.openSession()
        UserMapper userMapper = session.getMapper(UserMapper.class)

        when:
        List<User> users = userMapper.findAll()

        then:
        assert !users.empty
        assert users.get(0).password == "123456"
        users.forEach(System.out::println)
    }
}
