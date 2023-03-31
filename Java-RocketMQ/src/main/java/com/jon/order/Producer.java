package com.jon.order;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Producer {
    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("Litter");
        producer.setNamesrvAddr("192.168.0.45:9876;192.168.0.46:9876");
        producer.start();
        List<Order> orders = Order.buildOrders();
        for (Order order : orders) {
            String body = order.getDesc();
            Message msg = new Message("orderTopic", "Order", "key", body.getBytes());
            SendResult result = producer.send(msg, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                    long orderId = (long) o;
                    int index = (int) (orderId % list.size());
                    return list.get(index);
                }
            }, order.getOrderId());
            System.out.println("SendResult: " + result);
        }
        // 线程睡一秒，不要一直发送
        producer.shutdown();
    }
}
