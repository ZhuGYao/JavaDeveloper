package com.zgy.develop.reactivex;

/**
 * @author zgy
 * @data 2021/4/20 14:48
 */

public interface Function<T, R> {

    R call(T t);
}
