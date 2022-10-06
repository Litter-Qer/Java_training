package com.jon.springsource_5;

import com.jon.springsource_5.component.Bean2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bean1 {
    public static final Logger log = LoggerFactory.getLogger(Bean1.class);

    public Bean1() {
        log.debug("bean1 被spring管理了");
    }
}
