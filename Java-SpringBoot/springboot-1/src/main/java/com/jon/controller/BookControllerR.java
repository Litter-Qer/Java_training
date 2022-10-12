package com.jon.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jon.controller.utils.R;
import com.jon.domian.Book;
import com.jon.service.BookServiceMp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/books")
@RestController
public class BookControllerR {

    @Autowired
    private BookServiceMp bookServiceMp;

    @PostMapping
    public R save(@RequestBody Book book) {
        return new R(bookServiceMp.save(book));
    }

    @PutMapping
    public R update(@RequestBody Book book) {
        return new R(bookServiceMp.updateById(book));
    }

    @GetMapping
    public R getAll() {
        return new R(true, bookServiceMp.list());
    }

    @DeleteMapping("/{id}")
    public R delete(@PathVariable Integer id) {
        return new R(bookServiceMp.removeById(id));
    }

    @GetMapping("/{id}")
    public R getById(@PathVariable Integer id) {
        return new R(true, bookServiceMp.getById(id));
    }

    @GetMapping("/{currentPage}/{pageSize}")
    public R getPage(@PathVariable int currentPage, @PathVariable int pageSize) {
        return new R(true, bookServiceMp.getPage(currentPage, pageSize));
    }
}
