package com.jon.service.Impl;

import com.jon.controller.Code;
import com.jon.mapper.BookDao;
import com.jon.domain.Book;
import com.jon.exception.BusinessException;
import com.jon.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookDao bookDao;

    public boolean save(Book book) {
        bookDao.save(book);
        return true;
    }

    public boolean update(Book book) {
        bookDao.update(book);
        return true;
    }

    public boolean delete(Integer id) {
        bookDao.delete(id);
        return true;
    }

    public Book getById(Integer id) {
        try {
            int i = 1 / 0;
        } catch (Exception e) {
            throw new BusinessException(Code.BUSINESS_ERR, "好问题，但是还没法解决");
        }
        return bookDao.getById(id);
    }

    public List<Book> getAll() {
        return bookDao.getAll();
    }
}
