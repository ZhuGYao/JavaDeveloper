package com.zgy.develop.reactivex;

/**
 * @author zgy
 * @data 2021/4/22 17:28
 */


@FunctionalInterface
public interface BiFunction<T1, T2, R> {
    R call(T1 t1, T2 t2);
}
