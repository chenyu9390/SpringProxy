package com.ck.service;

import com.ck.annotation.Implement;

import java.lang.annotation.Inherited;

/**
 * springProxy
 * 2020/1/3 15:24
 *  接口实现
 * @author ck
 * @since
 **/
public class JavaDeveloper implements Developer {

    @Override
    public void code(String name) {
        System.out.println(name+" is coding java");
    }

    @Override
    public void debug(String name) {
        System.out.println(name+" is debugging java");
    }
}
