package com.jon.order;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;

public class Consumer {
    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
        consumer.setNamesrvAddr("192.168.0.45:9876;192.168.0.46:9876");
        consumer.subscribe("orderTopic", "*");
        consumer.registerMessageListener((MessageListenerOrderly) (messages, consumeConcurrentlyContext) -> {
            for (MessageExt msg : messages) {
                System.out.println(Thread.currentThread().getName() + " --- " + new String(msg.getBody()));
            }
            return ConsumeOrderlyStatus.SUCCESS;
        });
        consumer.start();
    }
}
