package com.jon

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.jon.mapper.UserMapper
import com.jon.pojo.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class TestUserMapper extends Specification {

    @Autowired
    private UserMapper userMapper;

    def "insert a record"() {
        given:
        def user = new User()
        user.setAge("15")
        user.setUserEmail("hotpot@hotpot.com")
        user.setName("Julie")
        user.setUserName("Lua")
        user.setPassword("8426")
        user.setAddress("24 Albert Road")

        when:
        def res = userMapper.insert(user)
        def users = userMapper.selectList(null)

        then:
        res == 1
        println users.last()
    }

    def "select a random user"() {
        when:
        def user = userMapper.selectById(1L)

        then:
        1 == 1
        println user
    }

    def "update by id"() {
        given:
        def user = new User();
        user.setId(1)
        user.setAge("20")
        user.setPassword("456789")

        when:
        def res = userMapper.updateById(user)
        def record = userMapper.selectById(1)

        then:
        println user
        println record
        res == 1
    }

    def "update by query wrapper with name"() {
        given:
        def user = new User();
        user.setAge("62")
        user.setPassword("1234")
        def wrapper = new QueryWrapper<User>()
        wrapper.eq("user_name", "b")

        when:
        def res = userMapper.update(user, wrapper)
        def record = userMapper.selectOne(wrapper)

        then:
        println user
        println record
        res == 1
    }

    def "update by update wrapper with set"() {
        given:
        def wrapper = new UpdateWrapper<User>()
        wrapper.set("age", "22").set("password", "456789")  // 更新的字段
                .eq("user_name", "c")    // 更新条件

        when:
        def res = userMapper.update(null, wrapper)
        def record = userMapper.selectOne(wrapper)

        then:
        println record
        res == 1
    }

    def "delete by id"() {
        given:
        def countBefore = userMapper.selectCount(null)

        when:
        def res = userMapper.deleteById(12)
        def countAfter = userMapper.selectCount(null)

        then:
        countBefore > countAfter
        countBefore - countAfter == 1
        res == 1
    }

    def "delete by map"() {
        given:
        def map = new HashMap<String, Object>()
        map.put("user_name", "Tyu")
        map.put("password", "1234")
        def countBefore = userMapper.selectCount(null)

        when:
        def res = userMapper.deleteByMap(map)
        def countAfter = userMapper.selectCount(null)

        then:
        countBefore > countAfter
        countBefore - countAfter == 1
        res == 1
    }

    def "delete by purely query wrapper"() {
        given:
        def wrapper = new QueryWrapper<User>()
        wrapper.eq("user_name", "Lua")
                .eq("password", "8426")
        def countBefore = userMapper.selectCount(null)

        when:
        def res = userMapper.delete(wrapper)
        def countAfter = userMapper.selectCount(null)

        then:
        countBefore > countAfter
        countBefore - countAfter == 1
        res == 1
    }

    def "delete by query wrapper"() {
        given:
        def user = new User()
        user.setPassword("951753")
        user.setUserName("Gudu")
        def wrapper = new QueryWrapper<User>(user)
        def countBefore = userMapper.selectCount(null)

        when:
        def res = userMapper.delete(wrapper)
        def countAfter = userMapper.selectCount(null)

        then:
        countBefore > countAfter
        countBefore - countAfter == 1
        res == 1
    }

    def "delete by batch id"() {
        given:
        def users = Arrays.asList(10L, 11L, 12L)
        def countBefore = userMapper.selectCount(null)

        when:
        def res = userMapper.deleteBatchIds(users)
        def countAfter = userMapper.selectCount(null)

        then:
        countBefore > countAfter
        countBefore - countAfter == res
    }

    def "select by batch ids"() {
        given:
        def ids = Arrays.asList(8L, 9L, 10L, 14L)

        when:
        def users = userMapper.selectBatchIds(ids)

        then:
        users.forEach(System.out::println)
    }

    def "select by query wrapper with conditions"() {
        given:
        def wrapper = new QueryWrapper<User>()
        wrapper.gt("age", "20")

        when:
        def count = userMapper.selectCount(wrapper)

        then:
        print count
    }

    def "select by query wrapper with list"() {
        given:
        def wrapper = new QueryWrapper<User>()
        wrapper.like("email", "hotpot")

        when:
        def users = userMapper.selectList(wrapper)

        then:
        users.forEach(System.out::println)
    }

    def "select by page"() {
        given:
        def page = new Page<User>(1, 2)
        def wrapper = new QueryWrapper<User>()
        wrapper.like("email", "hotpot")

        when:
        def iPage = userMapper.selectPage(page, wrapper)

        then:
        println "Current page: " + iPage.getCurrent()
        println "Page size: " + iPage.getSize()
        println "Total pages: " + iPage.getPages()
        println "Total records: "
        iPage.getRecords().forEach(System.out::println)
        println "Total size: " + iPage.getTotal()
    }

    def "find a user by id"() {
        when:
        def user = userMapper.findById(3L)

        then:
        println user
    }
}
