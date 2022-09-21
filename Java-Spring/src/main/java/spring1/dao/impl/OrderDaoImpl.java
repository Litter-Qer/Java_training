package spring1.dao.impl;

import spring1.dao.BookDao;
import spring1.dao.OrderDao;

public class OrderDaoImpl implements OrderDao {
    public OrderDaoImpl() {
        System.out.println("Creating Order Dao object");
    }

    public void save(){
        System.out.println("book dao save ...");
    }
}
