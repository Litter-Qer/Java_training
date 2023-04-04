package com.jon;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProducerTest {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Test
    public void sendMsg() {
        for (int i = 0; i <50; i++) {
            rocketMQTemplate.convertAndSend("springboot-rocketmq", "ez text");
        }
    }
}
