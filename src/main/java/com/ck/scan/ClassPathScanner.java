package com.ck.scan;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

/**
 * springProxy
 * 2020/1/3 15:00
 * 扫描自定义注解包
 *
 * @author ck
 * @since
 **/
public class ClassPathScanner extends ClassPathBeanDefinitionScanner {

    public ClassPathScanner(BeanDefinitionRegistry registry) {
        super(registry,false);
    }

    /**
     * 接口或者类才能被扫描到
     * @param beanDefinition
     * @return
     */
    @Override
    protected boolean isCandidateComponent(org.springframework.beans.factory.annotation.AnnotatedBeanDefinition beanDefinition){
        return beanDefinition.getMetadata().isConcrete() || beanDefinition.getMetadata().isInterface();
    }

}
