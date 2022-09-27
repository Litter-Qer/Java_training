package spring1.dao.impl;

import spring1.dao.BookDao;

public class BookDaoImpl3 implements BookDao {
    private String name;

    public BookDaoImpl3() {
        System.out.println("Creating Book Dao object");
    }

    public void save() {
        System.out.println("book dao save ..." + name);
    }

    public void setName(String name) {
        this.name = name;
    }
}
