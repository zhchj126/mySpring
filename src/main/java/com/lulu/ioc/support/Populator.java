package com.lulu.ioc.support;


import com.lulu.ioc.annotation.Autowire;

import java.lang.reflect.Field;
import java.util.Map;

public class Populator {

    public Populator() {
    }

    public void populate(Map<String, Object> instanceMapping) {
        //首先要判断ioc容器中有没有东西
        if (instanceMapping.isEmpty()) return;

        //循环遍历每一个容器中得对象
        for (Map.Entry<String, Object> entry : instanceMapping.entrySet()) {
            //获取对象的字段
            Field[] fields = entry.getValue().getClass().getDeclaredFields();
            for (Field field : fields) {
                if (!field.isAnnotationPresent(Autowire.class)) continue;
                Autowire autowire = field.getAnnotation(Autowire.class);
                //后去字段要注入的id value  为空则按类名  接口名自动注入
                String id = autowire.value();
                if ("".equals(id)) id = field.getType().getName();
                field.setAccessible(true);
                try {
                    //反射注入
                    field.set(entry.getValue(), instanceMapping.get(id));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}