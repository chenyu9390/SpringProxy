package com.ck.service;

import com.ck.annotation.Implement;

/**
 * springProxy
 * 2020/1/3 16:08
 *
 * @author ck
 * @since
 **/
@Implement
public class AudiCar implements Car {

    @Override
    public void color(String car) {
        System.out.println(car +" is red");
    }
}
