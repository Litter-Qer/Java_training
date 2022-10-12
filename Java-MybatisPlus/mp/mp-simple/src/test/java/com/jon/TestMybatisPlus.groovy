package com.jon

import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder
import com.jon.mapper.UserMapper
import com.jon.pojo.User
import org.apache.ibatis.io.Resources
import org.apache.ibatis.session.SqlSession
import org.apache.ibatis.session.SqlSessionFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import spock.lang.Specification

class TestMybatisPlus extends Specification {
    static final Logger log = LoggerFactory.getLogger(TestMybatisPlus.class);

    def "select list of users"() {
        given:
        String config = "mybatis-config.xml"
        InputStream inputStream = Resources.getResourceAsStream(config)
        SqlSessionFactory sqlSessionFactory = new MybatisSqlSessionFactoryBuilder().build(inputStream)

        SqlSession session = sqlSessionFactory.openSession()
        UserMapper userMapper = session.getMapper(UserMapper.class)

        when:
        List<User> users = userMapper.selectList(null)

        then:
        !users.empty
        users.get(0).password == "123456"
        log.debug("users" + users)
        users.forEach(System.out::println)
    }
}
