package com.jon.publisher;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Publish {
    public static final String QUEUE_NAME = "simple-queue";
    public static final String QUEUE_NAME2 = "simple-queue2";

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
        String message = "Hello world!";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());

        channel.queueDeclare(QUEUE_NAME2, false, false, false, null);
        channel.basicPublish("", QUEUE_NAME2, null, message.getBytes());

        System.out.println("Message -- " + message + " -- has been sent");
        channel.close();
        connection.close();
    }
}