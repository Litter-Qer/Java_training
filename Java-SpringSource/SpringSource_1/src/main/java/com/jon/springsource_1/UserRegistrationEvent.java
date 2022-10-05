package com.jon.springsource_1;

import org.springframework.context.ApplicationEvent;

public class UserRegistrationEvent extends ApplicationEvent {
    public UserRegistrationEvent(Object source) {
        super(source);
    }
}
