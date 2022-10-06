package com.jon.springsource_5.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


public class Bean4 {
    public static final Logger log = LoggerFactory.getLogger(Bean4.class);

    public Bean4() {
        log.debug("bean4 被spring管理了");
    }
}
