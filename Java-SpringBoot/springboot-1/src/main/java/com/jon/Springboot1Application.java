package com.jon;

import com.jon.controller.BookController;
import com.jon.pojo.User;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Springboot1Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Springboot1Application.class, args);
        BookController bookController = context.getBean(BookController.class);
        System.out.println("Bean =====> " + bookController);

        User user = context.getBean(User.class);
        System.out.println("Bean =====> " + user);
    }
}
