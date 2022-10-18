package com.jon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Springboot4Application {

    public static void main(String[] args) {
        SpringApplication.run(Springboot4Application.class, args);
    }

}
