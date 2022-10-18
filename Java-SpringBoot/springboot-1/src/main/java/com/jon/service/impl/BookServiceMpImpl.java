package com.jon.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jon.mapper.BookDao;
import com.jon.domian.Book;
import com.jon.service.BookServiceMp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceMpImpl extends ServiceImpl<BookDao, Book> implements BookServiceMp {
    @Autowired
    private BookDao bookDao;

    @Override
    public IPage<Book> getPage(int currentPage, int pageSize) {
        IPage<Book> page = new Page<>(currentPage, pageSize);
        return bookDao.selectPage(page, null);
    }
}
