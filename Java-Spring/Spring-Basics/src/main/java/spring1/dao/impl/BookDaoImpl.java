package spring1.dao.impl;

import spring1.dao.BookDao;

public class BookDaoImpl implements BookDao {
    private String name;
    private int id;

    public BookDaoImpl(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public BookDaoImpl() {
        System.out.println("Creating Book Dao object");
    }

    public void save() {
        System.out.println(name);
        System.out.println(id);
        System.out.println("book dao save ...");
    }

}
