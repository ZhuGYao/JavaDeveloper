package com.zgy.develop.reactivex;

/**
 * @author zgy
 * @data 2021/4/20 11:28
 */

@FunctionalInterface
public interface Call<T> {

    void call(T t);

}
