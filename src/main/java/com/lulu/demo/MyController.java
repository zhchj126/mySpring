package com.lulu.demo;

import com.lulu.ioc.ApplicationContext;
import com.lulu.ioc.annotation.Autowire;
import com.lulu.ioc.annotation.Controller;
import com.lulu.ioc.support.AnnotationApplicationContext;


@Controller
public class MyController {

    @Autowire("myService")
    MyService myService;

    public void test() {
        myService.say("hello ioc");
    }


}
