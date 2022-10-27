package com.jon

import com.jon.service.BookService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class ReadOnlyTest extends Specification {
    @Autowired
    BookService bookService;

    def "read only B to A"() {
        given:
        println "before update ---- " + bookService.getById(8)

        when:
        bookService.getAndUpdate(8)

        then:
        println "after update ---- " + bookService.getById(8)
    }

    def "update a record"() {
        given:
        def book = bookService.getById 8
        book.setDescription("test test")

        when:
        println "before update ---- " + book
        bookService.updateById(book)

        then:
        println "after update ---- " + bookService.getById(8)
    }

    def "insert a book"() {
        given:
        bookService.findAllBooks().forEach(System.out::println)

        when:
        bookService.insert()

        then:
        bookService.findAllBooks().forEach(System.out::println)
    }
}
