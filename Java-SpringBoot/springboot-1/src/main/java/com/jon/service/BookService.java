package com.jon.service;

import com.jon.domian.Book;

import java.util.List;

public interface BookService {
    Boolean save(Book book);

    Boolean update(Book book);

    Boolean delete(Book book);

    Book getById(Integer id);

    List<Book> getAll();
}
