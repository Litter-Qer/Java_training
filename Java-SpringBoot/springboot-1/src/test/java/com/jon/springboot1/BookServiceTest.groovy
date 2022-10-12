package com.jon.springboot1


import com.jon.domian.Book
import com.jon.service.BookService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class BookServiceTest extends Specification {

    @Autowired
    private BookService bookService

    def "select book by id"() {
        expect:
        bookService.getById(id as Integer) instanceof Book

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
        def rst = bookService.save(book)

        then:
        rst
    }

    def "get all books"() {
        given:
        println "Start selecting all data"

        when:
        def books = bookService.getAll()

        then:
        books.forEach(System.out::println)
    }

    def "delete a book"() {
        given:
        def book = new Book();
        book.setId(4)

        when:
        def rst = bookService.delete(book)

        then:
        rst
    }
}
