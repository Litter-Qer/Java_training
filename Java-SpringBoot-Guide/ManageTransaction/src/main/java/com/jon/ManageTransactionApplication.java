package com.jon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
public class ManageTransactionApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManageTransactionApplication.class, args);
    }

}
