package com.jon.springsource_1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class CompA {
    public static final Logger log = LoggerFactory.getLogger(CompA.class);

    @Autowired
    private ApplicationEventPublisher context;

    public void register() {
        log.debug("User Register");
        context.publishEvent(new UserRegistrationEvent(this));
    }
}
