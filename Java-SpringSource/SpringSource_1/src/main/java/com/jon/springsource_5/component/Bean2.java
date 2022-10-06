package com.jon.springsource_5.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Bean2 {
    public static final Logger log = LoggerFactory.getLogger(Bean2.class);

    public Bean2() {
        log.debug("bean2 被spring管理了");
    }
}
