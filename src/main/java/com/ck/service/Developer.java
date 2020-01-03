package com.ck.service;

import com.ck.annotation.InterfaceService;

import java.lang.annotation.Inherited;

/**
 * springProxy
 * 2020/1/3 15:23
 *  测试接口
 * @author ck
 * @since
 **/
@InterfaceService
public interface Developer {
    void code(String name);
    void debug(String name);
}
