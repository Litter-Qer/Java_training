package com.jon.springboot1

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.jon.mapper.BookDao
import com.jon.domian.Book
import org.apache.logging.log4j.util.Strings
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class SpringbootDruidApplication extends Specification {

    @Autowired
    private BookDao bookDao;

    def "select one book"() {
        when:
        println "data granted"

        then:
        println "Book: " + bookDao.selectById(1)
    }

    def "select pages"() {
        when:
        IPage page = new Page(2, 3)

        then:
        def selectPage = bookDao.selectPage(page, null)

        println selectPage.getRecords()
        printf "Total number of records: %d %n", selectPage.getTotal()
        printf "Size of each page: %d %n", selectPage.getSize()
        printf "Current page: %d %n", selectPage.getCurrent()
        printf "Total number of pages: %d %n", selectPage.getPages()
    }

    def "get records by query wrapper"() {
        when:
        def queryWrapper = new QueryWrapper<Book>()
        queryWrapper.like("name", "litt")
        def books = bookDao.selectList(queryWrapper)

        then:
        books.forEach(System.out::println)
    }

    def "get records by lambda query wrapper"() {
        given:
        def name = "litt"
        def query = new LambdaQueryWrapper<Book>()
        query.like(Strings.isNotEmpty(name), Book::getName, name)

        when:
        def books = bookDao.selectList(query)

        then:
        books.forEach(System.out::println)
    }
}
