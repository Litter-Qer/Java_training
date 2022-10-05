package com.jon.springsource_3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application3 {
    public static final Logger log = LoggerFactory.getLogger(Application3.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application3.class, args);
        context.close();
    }

}
