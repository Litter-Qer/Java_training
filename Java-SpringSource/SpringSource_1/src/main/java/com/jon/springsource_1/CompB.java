package com.jon.springsource_1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CompB {
    public static final Logger log = LoggerFactory.getLogger(CompB.class);

    @EventListener
    public void registerListener(UserRegistrationEvent event) {
        log.debug("{}", event);
        log.debug("send a message to user");
    }
}
