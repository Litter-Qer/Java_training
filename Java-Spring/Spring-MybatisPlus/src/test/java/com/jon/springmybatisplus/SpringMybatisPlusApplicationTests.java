package com.jon.springmybatisplus;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jon.springmybatisplus.dao.UserDao;
import com.jon.springmybatisplus.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SpringMybatisPlusApplicationTests {

    @Autowired
    private UserDao userDao;

    @Test
    void contextLoads() {
        List<User> users = userDao.selectList(null);
        users.forEach(System.out::println);
    }

    @Test
    void saveTest() {
        User user = new User();
        user.setAge(10);
        user.setName("Tommy");
        user.setTel("0431000005");
        user.setPassword("123456");
        userDao.insert(user);
    }

    @Test
    void deleteTest() {
        userDao.deleteById(1575737059464421378L);
    }

    @Test
    void updateTest() {
        User user = new User();
        user.setId(1L);
        user.setAge(20);
        user.setName("Tommy_1");
        user.setTel("0431000005");
        user.setPassword("123456");
        userDao.updateById(user);
    }

    @Test
    void getByIdTest() {
        System.out.println(userDao.selectById(1));
    }

    @Test
    void testGetByPage() {
        IPage page = new Page(1, 2);
        userDao.selectPage(page, null);
        System.out.println(page.getCurrent());
        System.out.println(page.getSize());
        System.out.println(page.getPages());
        System.out.println(page.getTotal());
        System.out.println(page.getRecords());
    }

}
