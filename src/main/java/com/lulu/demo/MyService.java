package com.lulu.demo;

import com.lulu.ioc.annotation.Component;


@Component(value = "myService")
public class MyService {
    public void say(String str) {
        System.out.println(str);
    }
}
