package com.jon.springsource_3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyBeanPostProcessor implements InstantiationAwareBeanPostProcessor, DestructionAwareBeanPostProcessor {
    public static final Logger log = LoggerFactory.getLogger(MyBeanPostProcessor.class);

    @Override
    public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
        if (beanName.equals("BeanLifeCycle"))
            log.debug(">>>>>>>>>>>>>>销毁bean之前执行这个");
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if (beanName.equals("BeanLifeCycle"))
            log.debug(">>>>>>>>>>>>>>实例化之前执行，返回对象会替换掉原来的Bean");
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if (beanName.equals("BeanLifeCycle")) {
            log.debug(">>>>>>>>>>>>>>实例化之后执行，如果返回false就会跳过依赖注入");
//            return false;
        }
        return true;
    }

    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        if (beanName.equals("BeanLifeCycle"))
            log.debug(">>>>>>>>>>>>>>依赖注入阶段执行，@Autowired等");
        return pvs;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("BeanLifeCycle"))
            log.debug(">>>>>>>>>>>>>>初始化之前执行，返回的对象会替换掉原来的Bean");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("BeanLifeCycle"))
            log.debug(">>>>>>>>>>>>>>初始化之后执行，返回的对象会替换掉原来的Bean");
        return bean;
    }


}
