package com.jon.springsource_6;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.GenericApplicationContext;

public class Application6 {
    public static final Logger log = LoggerFactory.getLogger(Application6.class);

    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean("myBean", MyBean.class);

        context.refresh();
        context.close();
    }

}