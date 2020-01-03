package com.ck.annotation;

import java.lang.annotation.*;

/**
 * springProxy
 * 2020/1/3 14:55
 * 实现接口的自定义注解
 *
 * @author ck
 * @since
 **/
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Implement {
}
