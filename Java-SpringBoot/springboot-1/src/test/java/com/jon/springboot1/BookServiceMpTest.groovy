package com.jon.springboot1

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.jon.domian.Book
import com.jon.service.BookServiceMp
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class BookServiceMpTest extends Specification {

    @Autowired
    private BookServiceMp bookServiceMp

    def "select book by id"() {
        expect:
        bookServiceMp.getById(id as Integer) instanceof Book

        where:
        id << [1, 2, 3]
    }

    def "save a book"() {
        given:
        def book = new Book();
        book.setName("ABC Murder")
        book.setDescription("Good Book")
        book.setType("book")

        when:
        def rst = bookServiceMp.save(book)

        then:
        rst
    }

    def "get all books"() {
        given:
        println "Start selecting all data"

        when:
        def books = bookServiceMp.findAll()

        then:
        books.forEach(System.out::println)
    }

    def "delete a book"() {
        given:
        def query = new QueryWrapper<Book>()
        query.like("name", "ABC")

        when:
        def rst = bookServiceMp.remove(query)

        then:
        rst
    }

    def "get a page of books"() {
        given:
        IPage<Book> page = new Page<>(1, 5)

        when:
        def myPage = bookServiceMp.page(page)

        then:
        println myPage.getRecords()
        println myPage.getPages()
    }
}
