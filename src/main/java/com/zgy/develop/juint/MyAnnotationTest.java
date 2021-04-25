package com.zgy.develop.juint;

import com.zgy.develop.annotation.unit.MyAfter;
import com.zgy.develop.annotation.unit.MyBefore;
import com.zgy.develop.annotation.unit.MyTest;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 朱光耀
 * @data 2021/4/14 20:02
 */

public class MyAnnotationTest {

    @MyBefore
    public void before() {
        System.out.println("before");
    }

    @MyTest
    public void test() {
        System.out.println("test");
    }

    @MyTest
    public void test2() {
        System.out.println("test2");
    }

    @MyAfter
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
            if (m.isAnnotationPresent(MyBefore.class)) {
                beforeList.add(m);
            } else if (m.isAnnotationPresent(MyAfter.class)) {
                afterList.add(m);
            } else if (m.isAnnotationPresent(MyTest.class)) {
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
