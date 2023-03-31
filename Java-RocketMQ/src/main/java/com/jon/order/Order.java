package com.jon.order;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Order {
    private long orderId;
    private String desc;

    public static List<Order> buildOrders() {
        // 1001 created pay posted done
        // 1002 created
        // 1003 pay
        List<Order> orders = new ArrayList<>();
        Order order = new Order();
        order.setOrderId(1001L);
        order.setDesc("created");
        orders.add(order);

        order = new Order();
        order.setOrderId(1002L);
        order.setDesc("created");
        orders.add(order);

        order = new Order();
        order.setOrderId(1001L);
        order.setDesc("pay");
        orders.add(order);

        order = new Order();
        order.setOrderId(1001L);
        order.setDesc("posted");
        orders.add(order);

        order = new Order();
        order.setOrderId(1003L);
        order.setDesc("pay");
        orders.add(order);

        order = new Order();
        order.setOrderId(1001L);
        order.setDesc("done");
        orders.add(order);

        return orders;
    }
}
