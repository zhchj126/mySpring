package com.lulu.ioc.support;


import com.lulu.ioc.ApplicationContext;
import com.lulu.ioc.BeanRegister;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class AnnotationApplicationContext implements ApplicationContext, BeanRegister {
    private Map<String, Object> instanceMapping = new ConcurrentHashMap<String, Object>();

    //保存所有bean的信息,主要包含bean的类型  id等信息
    private List<BeanDefinition> beanDefinitions = new ArrayList<BeanDefinition>();
    //配置文件的config,这里为了简单我们使用properties文件
    private Properties config = new Properties();

    public AnnotationApplicationContext(String location) {
        InputStream is = null;
        try {

            //1、定位
            is = this.getClass().getClassLoader().getResourceAsStream(location);

            //2、载入
            config.load(is);

            //3、注册
            register();

            //4、实例化
            createBean();

            //5、注入
            populate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 调用具体委派的注入类进行注入
     */
    private void populate() {
        Populator populator = new Populator();
        populator.populate(instanceMapping);
    }

    /**
     * 调用具体的创建对象创建bean
     */
    private void createBean() {
        BeanCreater creater = new BeanCreater(this);
        creater.create(beanDefinitions);
    }

    /**
     * 调用具体的注册对象注册bean信息
     */
    private void register() {
        BeanDefinitionParser parser = new BeanDefinitionParser(this);
        parser.parse(config);
    }

    public Object getBean(String id) {
        return instanceMapping.get(id);
    }

    public Properties getConfig() {
        return this.config;
    }

    public <T> T getBean(String id, Class<T> clazz) {
        return (T) instanceMapping.get(id);
    }

    public Map<String, Object> getBeans() {
        return instanceMapping;
    }

    public void registBeanDefinition(List<BeanDefinition> bds) {
        beanDefinitions.addAll(bds);
    }

    public void registInstanceMapping(String id, Object instance) {
        instanceMapping.put(id, instance);
    }

}
