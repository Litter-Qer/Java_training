package com.jon.springsource_5.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Bean3 {
    public static final Logger log = LoggerFactory.getLogger(Bean3.class);

    public Bean3() {
        log.debug("bean3 被spring管理了");
    }
}
