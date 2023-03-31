package com.jon.base.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.concurrent.TimeUnit;

public class OnewayProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("async-producer");
        producer.setNamesrvAddr("192.168.0.45:9876;192.168.0.46:9876");
        producer.start();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            Message msg = new Message();
            msg.setTopic("base");
            msg.setTags("tag3");
            msg.setBody(("one way msg" + i).getBytes());
            producer.sendOneway(msg);
            // 线程睡一秒，不要一直发送
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
