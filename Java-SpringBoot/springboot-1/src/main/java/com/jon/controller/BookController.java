package com.jon.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jon.domian.Book;
import com.jon.pojo.User;
import com.jon.service.BookServiceMp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RequestMapping(value = "/books")
//@RestController
public class BookController {

    @Autowired
    private BookServiceMp bookServiceMp;

    @PostMapping
    public Boolean save(@RequestBody Book book) {
        return bookServiceMp.save(book);
    }

    @PutMapping
    public Boolean update(@RequestBody Book book) {
        return bookServiceMp.updateById(book);
    }

    @GetMapping
    public List<Book> getAll() {
        return bookServiceMp.list();
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return bookServiceMp.removeById(id);
    }

    @GetMapping("/{id}")
    public Book getById(@PathVariable Integer id) {
        return bookServiceMp.getById(id);
    }

    @GetMapping("/{currentPage}/{pageSize}")
    public IPage<Book> getPage(@PathVariable int currentPage, @PathVariable int pageSize) {
        return bookServiceMp.getPage(currentPage, pageSize);
    }
}
