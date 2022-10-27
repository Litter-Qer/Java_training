package com.jon.service;

import com.jon.mapper.BookMapper;
import com.jon.pojo.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Slf4j
@Service
public class BookService {
    final BookMapper bookMapper;

    public BookService(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public Book getById(Integer id) {
        return bookMapper.selectById(id);
    }

//    @Transactional(propagation = Propagation.REQUIRED)
//    public void updateById(Integer id) {
//        Book book = new Book(id);
//        bookMapper.updateById(book);
//    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateById(Book book) {
        System.out.println("Updating");
        bookMapper.updateById(book);
        insert();
        System.out.println("Updated");
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void getAndUpdate(Integer id) {
        Book book = getById(id);
        book.setDescription("book: " + new Random().nextInt(1000));
        updateById(book);
        System.out.println(getById(id));
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public void insert() {
        bookMapper.insert(new Book("abc", "test", "b" + new Random().nextInt(1000)));
    }

    public List<Book> findAllBooks() {
        return bookMapper.selectList(null);
    }
}
