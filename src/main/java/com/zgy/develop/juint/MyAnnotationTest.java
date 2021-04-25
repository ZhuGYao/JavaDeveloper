package com.zgy.annotation;

import com.zgy.enums.TypeEnum;

/**
 * @author 朱光耀
 * @data 2021/4/14 20:02
 */

@MyAnnotation(value = "shit", type = TypeEnum.NORMAL)
public class MyAnnotationTest {

    @MyBefore
    public void before() {
        System.out.println("before");
    }

    @MyTest
    public void test() {
        System.out.println("test");
    }

    @MyAfter
    public void after() {
        System.out.println("after");
    }
}
