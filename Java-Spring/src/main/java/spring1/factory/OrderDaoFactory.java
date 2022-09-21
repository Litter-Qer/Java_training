package spring1.factory;

import spring1.dao.OrderDao;
import spring1.dao.impl.OrderDaoImpl;

public class OrderDaoFactory {

//    public static OrderDao getOrderDao() {
//        return new OrderDaoImpl();
//    }

    public OrderDao getOrderDao() {
        return new OrderDaoImpl();
    }

}
