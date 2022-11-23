package com.jon.publisher

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class SpringAMQPTest extends Specification {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    def "test send a message"() {
        when:
        String queueName = "queue1"
        String message = "helloworld"

        then:
        rabbitTemplate.convertAndSend(queueName, message)
    }
}
