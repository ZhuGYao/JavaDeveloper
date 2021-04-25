package com.zgy.develop.juint;

import com.zgy.develop.annotation.unit.After;
import com.zgy.develop.annotation.unit.Before;
import com.zgy.develop.annotation.unit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 朱光耀
 * @data 2021/4/14 20:02
 */

public class MyAnnotationTest {

    @Before
    public void before() {
        System.out.println("before");
    }

    @Test
    public void test() {
        System.out.println("test");
    }

    @Test
    public void test2() {
        System.out.println("test2");
    }

    @After
    public void after() {
        System.out.println("after");
    }

    public static void main(String[] args)  throws Exception {
        Class<MyAnnotationTest> myAnnotationTestClass = MyAnnotationTest.class;
        MyAnnotationTest myAnnotationTest = myAnnotationTestClass.newInstance();

        Method[] methods = myAnnotationTestClass.getMethods();
        List<Method> beforeList = new ArrayList<>();
        List<Method> afterList = new ArrayList<>();
        List<Method> testList = new ArrayList<>();
        for (Method m : methods) {
            if (m.isAnnotationPresent(Before.class)) {
                beforeList.add(m);
            } else if (m.isAnnotationPresent(After.class)) {
                afterList.add(m);
            } else if (m.isAnnotationPresent(Test.class)) {
                testList.add(m);
            }
        }

        for (Method m : testList) {
            for (Method b : beforeList) {
                b.invoke(myAnnotationTest);
            }
            m.invoke(myAnnotationTest);
            for (Method a : afterList) {
                a.invoke(myAnnotationTest);
            }
        }
    }
}
