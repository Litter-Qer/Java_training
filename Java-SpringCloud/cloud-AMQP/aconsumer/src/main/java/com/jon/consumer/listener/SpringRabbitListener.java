package com.jon.consumer.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class SpringRabbitListener {

    @RabbitListener(queues = "queue1")
    public void listenSimpleQueue(String msg) {
        System.out.println("consumer: " + msg);

    }
}
