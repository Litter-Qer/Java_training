package com.jon.dao.impl;

import com.jon.dao.BookDao;
import org.springframework.stereotype.Repository;

@Repository
public class BookDaoImpl implements BookDao {

    @Override
    public void save() {
//        System.out.println(System.currentTimeMillis());
        System.out.println("book dao save ...");
    }

    @Override
    public void update() {
        System.out.println("book dao update ...");
    }

    @Override
    public int select() {
        System.out.println("book Dao is running ...");
//        int i = 1/0;
        return 100;
    }
}
