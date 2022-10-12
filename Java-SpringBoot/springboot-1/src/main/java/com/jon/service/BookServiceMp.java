package com.jon.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jon.domian.Book;

public interface BookServiceMp extends IService<Book> {
    IPage<Book> getPage(int currentPage, int pageSize);
}
