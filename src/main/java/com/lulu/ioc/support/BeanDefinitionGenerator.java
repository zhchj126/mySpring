package com.lulu.ioc.support;


import com.lulu.ioc.annotation.Component;
import com.lulu.ioc.annotation.Controller;

import java.util.ArrayList;
import java.util.List;

public class BeanDefinitionGenerator {

    public static List<BeanDefinition> generate(String className) {
        try {
            Class clazz = Class.forName(className);
            String[] ids = generateIds(clazz);
            if (ids == null) return null;
            List<BeanDefinition> list = new ArrayList<BeanDefinition>();
            for (String id : ids) {
                list.add(new BeanDefinition(id, clazz));
            }
            return list;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 生成id数组
     * 1.带有@Controller 注解但是注解value没给值,@Controller一般没有
     * 接口定义,用类的全名作为id返回ids长度为1
     * 2.@Component 没有value  获取所有的实现的接口,接口名为id,返回ids数组
     * 长度是实现的接口个数
     * 3.@Component 有value 返回id=value
     * 4.不带容器要实例化的注解  null
     */
    private static String[] generateIds(Class clazz) {
        String[] ids = null;
        if (clazz.isAnnotationPresent(Controller.class)) {
            ids = new String[]{clazz.getName()};
        } else if (clazz.isAnnotationPresent(Component.class)) {
            Component component = (Component) clazz.getAnnotation(Component.class);
            String value = component.value();
            if (!"".equals(value)) {
                ids = new String[]{value};
            } else {
                Class<?>[] interfaces = clazz.getInterfaces();
                ids = new String[interfaces.length];
                //如果这个类实现了接口，就用接口的类型作为id
                for (int i = 0; i < interfaces.length; i++) {
                    ids[i] = interfaces[i].getName();
                }
                return ids;
            }
        }
        return ids;
    }

}