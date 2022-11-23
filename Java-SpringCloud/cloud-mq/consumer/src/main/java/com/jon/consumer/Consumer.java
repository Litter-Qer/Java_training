package com.jon.consumer;

import com.rabbitmq.client.*;

public class Consumer {
    public static final String QUEUE_NAME = "simple-queue";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.0.146");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setUsername("jon");
        factory.setPassword("123");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicConsume(QUEUE_NAME, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) {
                String message = new String(body);
                System.out.println("Received: " + message);
            }
        });
        System.out.println("Waiting");

        channel.close();
        connection.close();
    }
}
