package com.jon.springsource_1;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Map;

@SpringBootApplication
public class SpringSource1Application {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, IOException {
        ConfigurableApplicationContext context = SpringApplication.run(SpringSource1Application.class, args);
        System.out.println(context);
        Field singletonObject = DefaultSingletonBeanRegistry.class.getDeclaredField("singletonObjects");
        singletonObject.setAccessible(true);
        ConfigurableBeanFactory beanFactory = context.getBeanFactory();
        Map<String, Object> map = (Map<String, Object>) singletonObject.get(beanFactory);
        map.entrySet().stream().filter(e -> e.getKey().startsWith("comp"))
                .forEach(entry -> System.out.println(entry.getKey() + " => " + entry.getValue()));


        System.out.println(context.getMessage("hi", null, Locale.CHINA));
        System.out.println(context.getMessage("hi", null, Locale.ENGLISH));
        System.out.println(context.getMessage("hi", null, Locale.JAPANESE));

        Resource[] resources = context.getResources("classpath:application.yaml");
        for (Resource resource : resources) {
            System.out.println(resource);
        }

        System.out.println(context.getEnvironment().getProperty("java_home"));
        System.out.println(context.getEnvironment().getProperty("username"));

//        context.publishEvent(new UserRegistrationEvent(context));
        CompA compA = context.getBean(CompA.class);
        compA.register();
    }
}
