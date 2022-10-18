package com.jon.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jon.dao.BookMapper;
import com.jon.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookMapper bookMapper;

    @GetMapping("{id}")
    public Book get(@PathVariable Integer id) {
        return bookMapper.selectById(id);
    }

    @GetMapping()
    public List<Book> getAll() {
        QueryWrapper<Book> query = new QueryWrapper<>();
        query.select();
        return bookMapper.selectList(query);
    }
}
