package com.jon;

import com.alibaba.druid.pool.DruidDataSource;
import com.jon.config.ServerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Springboot2Application {
    @Bean
    @ConfigurationProperties(prefix = "datasource")
    public DruidDataSource dataSource() {
        DruidDataSource ds = new DruidDataSource();
//        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return ds;
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Springboot2Application.class, args);
//        ServerConfig bean = context.getBean(ServerConfig.class);
//        System.out.println(bean);
//        DruidDataSource ds = context.getBean(DruidDataSource.class);
//        System.out.println(ds.getDriverClassName());
    }

}
