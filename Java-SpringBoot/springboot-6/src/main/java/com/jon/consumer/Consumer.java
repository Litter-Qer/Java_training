package com.jon.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(topic = "springboot-rocketmq", consumerGroup = "${rocketmq.consumer.group}",
        consumeMode = ConsumeMode.CONCURRENTLY)
public class Consumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String msg) {
        log.info("Read msg from mq: " + msg);
    }
}

