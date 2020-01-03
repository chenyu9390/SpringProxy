package com.ck.annotation;

import java.lang.annotation.*;

/**
 * springProxy
 * 用于接口的注解
 * 2020/1/3 14:51
 *
 * @author ck
 * @since
 **/
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface InterfaceService {

}
