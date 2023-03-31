package com.jon.base.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.concurrent.TimeUnit;

/**
 * 同步消息
 */
public class SyncProducer {
    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("Litter");
        producer.setNamesrvAddr("192.168.0.45:9876;192.168.0.46:9876");
        producer.start();
        for (int i = 0; i < 10; i++) {
            Message msg = new Message();
            msg.setTopic("base");
            msg.setTags("tag1");
            msg.setBody(("Just a test msg" + i).getBytes());
            SendResult result = producer.send(msg);
            SendStatus status = result.getSendStatus();
            String msgId = result.getMsgId();
            int queueId = result.getMessageQueue().getQueueId();
            //System.out.println("status: " + status + " msg ID: " + msgId + " queue ID: " + queueId);
            //System.out.println(result);
            // 线程睡一秒，不要一直发送
            TimeUnit.MILLISECONDS.sleep(500);
        }
        producer.shutdown();
    }
}
