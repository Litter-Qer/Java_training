package com.jon.service;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceRocketMq implements MessageService {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public void sendMsg(String id) {
        System.out.println("发送短信的订单已纳入处理队列 :" + id);
        rocketMQTemplate.convertAndSend("order_id", id);
    }

    @Override
    public String doMsg() {
        return null;
    }
}
