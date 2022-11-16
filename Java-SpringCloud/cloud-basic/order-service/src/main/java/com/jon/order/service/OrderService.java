package com.jon.order.service;

import com.jon.feign.clients.UserClient;
import com.jon.feign.pojo.User;
import com.jon.order.mapper.OrderMapper;
import com.jon.order.pojo.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OrderService {

    @Resource
    private OrderMapper orderMapper;

//    @Autowired
//    private RestTemplate restTemplate;

//    public Order queryOrderById(Long orderId) {
//        Order order = orderMapper.findById(orderId);
//        String url = "http://user-service/user/" + order.getUserId();
//        User user = restTemplate.getForObject(url, User.class);
//        order.setUser(user);
//        return order;
//    }

    @Resource
    private UserClient userClient;

    public Order queryOrderById(Long orderId) {
        Order order = orderMapper.findById(orderId);
        User user = userClient.findById(orderId);
        order.setUser(user);
        return order;
    }

}
