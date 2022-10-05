package com.jon.springsource_4;

import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.context.support.GenericApplicationContext;

public class Application4 {
    public static void main(String[] args) {
        // 创建一个空的容器
        GenericApplicationContext context = new GenericApplicationContext();

        // 手动创建Bean
        context.registerBean("bean1", Bean1.class);
        context.registerBean("bean2", Bean2.class);
        context.registerBean("bean3", Bean3.class);
        context.registerBean("bean4", Bean4.class);

        // 手动添加 注解 Bean
        context.getDefaultListableBeanFactory().setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());
        context.registerBean(AutowiredAnnotationBeanPostProcessor.class);   // @Autowired, @Value
        context.registerBean(CommonAnnotationBeanPostProcessor.class);  // @Resource, @PreDestroy, @PostConstruct

        // 后处理器
        ConfigurationPropertiesBindingPostProcessor.register(context.getDefaultListableBeanFactory());

        // 初始化容器
        context.refresh();
        System.out.println(context.getBean(Bean4.class));

        // 销毁容器
        context.close();
    }
}
