package com.jon.contoller;

import com.jon.domain.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/books")
public class BookController {

    @GetMapping
    public Book getById() {
//        log.debug("hot deploy success ------ 1");
//        log.debug("hot deploy success ------ 2");
        log.debug("get by id running ...");
        Book book = new Book();
        book.setId(1);
        book.setName("java basics");
        book.setType("textbook");
        book.setDescription("learn java basic with java basics");
        return book;
    }

}
