package com.ck.annotation;

import java.lang.annotation.*;

/**
 * springProxy
 * 2020/1/3 14:55
 * 注解扫描路径
 *
 * @author ck
 * @since
 **/
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ScanPackage {

    String[] packages() default  {};
}
