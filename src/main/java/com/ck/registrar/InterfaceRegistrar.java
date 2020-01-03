package com.ck.registrar;

import com.ck.annotation.InterfaceService;
import com.ck.annotation.ScanPackage;
import com.ck.scan.ClassPathScanner;
import com.ck.util.ClassNameUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

/**
 * springProxy
 * 2020/1/3 15:04
 * 基于接口的动态注入
 * @author ck
 * @since
 **/
@Slf4j
public class InterfaceRegistrar implements BeanFactoryAware,ImportBeanDefinitionRegistrar,ResourceLoaderAware {

    private ResourceLoader resourceLoader;
    private BeanFactory beanFactory;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(ScanPackage.class.getName()));
        ClassPathScanner scanner = new ClassPathScanner(beanDefinitionRegistry);
        scanner.addIncludeFilter(new AnnotationTypeFilter(InterfaceService.class));
        if (resourceLoader != null) {
            scanner.setResourceLoader(resourceLoader);
        }
        List<String> basePackages = new ArrayList<String>();
        for (String pkg : annotationAttributes.getStringArray("packages")) {
            if (StringUtils.hasText(pkg)) {
                basePackages.add(pkg);
            }
        }
        Set<BeanDefinition> beanDefinitions = scanPackages(annotationMetadata,scanner);
        for(BeanDefinition beanDefinitionHolder : beanDefinitions){
            String className = beanDefinitionHolder.getBeanClassName();
            try {
                Class target  = Class.forName(className);
                Object proxy = Proxy.newProxyInstance(target.getClassLoader(),new Class[]{target}, new IServiceProxy(target));
                ((DefaultListableBeanFactory) beanFactory).registerSingleton(ClassNameUtils.beanName(className), proxy);
            } catch (Exception e) {
                e.printStackTrace();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    /**
     * 包扫描
     */
    private Set<BeanDefinition> scanPackages(AnnotationMetadata importingClassMetadata, ClassPathBeanDefinitionScanner scanner) {
        List<String> packages = new ArrayList<>();
        Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(ScanPackage.class.getCanonicalName());
        if (annotationAttributes != null) {
            String[] basePackages = (String[]) annotationAttributes.get("packages");
            if (basePackages.length > 0) {
                packages.addAll(Arrays.asList(basePackages));
            }
        }
        Set<BeanDefinition> beanDefinitions = new HashSet<>();
        if (CollectionUtils.isEmpty(packages)) {
            return beanDefinitions;
        }
        packages.forEach(pack -> beanDefinitions.addAll(scanner.findCandidateComponents(pack)));
        return beanDefinitions;
    }

    /**
     * java反射代理
     */
    static class IServiceProxy implements InvocationHandler {

        private Class target;

        IServiceProxy(Class target){
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            //基于接口什么都不返回
            log.info("InterfaceRegistrar 方法名称:"+method.getName());
            log.info("InterfaceRegistrar 方法参数："+method.getParameterCount());
            return "没有返回";
        }
    }
}
