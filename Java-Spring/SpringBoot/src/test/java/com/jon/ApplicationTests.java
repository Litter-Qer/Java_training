package com.jon;

import com.jon.dao.BookDao;
import com.jon.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {
    @Autowired
    private BookService bookService;

    @Autowired
    private BookDao bookDao;

    @Test
    void contextLoads() {
        bookService.save();
        System.out.println(bookDao.getById(12));
    }

}
