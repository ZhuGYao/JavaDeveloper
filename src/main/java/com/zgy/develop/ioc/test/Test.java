package com.zgy.develop.ioc.test;

import com.zgy.develop.annotation.ioc.ComponentScan;
import com.zgy.develop.ioc.CustomBeanFactory;

/**
 * @author zgy
 * @data 2021/4/25 15:24
 */

@ComponentScan("com.zgy.develop.ioc.test")
public class Test {

    public static void main(String[] args) {
        ComponentScan annotation = Test.class.getAnnotation(ComponentScan.class);
        CustomBeanFactory customBeanFactory = new CustomBeanFactory(annotation.value());
//        TestService testService = (TestService) customBeanFactory.getBean("testService");
        TestService testService = customBeanFactory.getBean(TestService.class);
        testService.test();
    }
}
