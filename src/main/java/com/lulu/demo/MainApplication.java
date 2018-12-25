package com.lulu.demo;

import com.lulu.ioc.ApplicationContext;
import com.lulu.ioc.support.AnnotationApplicationContext;

/**
 * Created by zcj on 2018/12/24.
 */
public class MainApplication {


    public static void main(String[] args) {
        ApplicationContext context = new AnnotationApplicationContext("applicationContext.properties");
        MyController controller = context.getBean("com.lulu.demo.MyController", MyController.class);
        controller.test();
    }

}
