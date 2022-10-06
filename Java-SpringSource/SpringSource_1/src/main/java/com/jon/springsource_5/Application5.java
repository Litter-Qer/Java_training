package com.jon.springsource_5;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.GenericApplicationContext;

import java.io.IOException;

public class Application5 {
    public static final Logger log = LoggerFactory.getLogger(Application5.class);

    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean("config", Config.class);

        // PostProcessors
        context.registerBean(ComponentScanPostProcessor.class);
        context.registerBean(AtBeanPostProcessor.class);
        context.registerBean(MapperBeanPostProcessor.class);

        // refresh
        context.refresh();

        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }

        context.close();
    }
}
