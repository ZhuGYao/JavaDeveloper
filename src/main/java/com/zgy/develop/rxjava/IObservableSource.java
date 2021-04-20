package com.zgy.develop.rxjava;

/**
 * @author zgy
 * @data 2021/4/20 10:16
 */

public interface ObservableSource<T> {

    void subscribe(Observer<? super T> observer);

}
