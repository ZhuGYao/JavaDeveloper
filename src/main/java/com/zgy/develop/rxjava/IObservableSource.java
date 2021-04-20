package com.zgy.develop.rxjava;

/**
 * @author zgy
 * @data 2021/4/20 10:16
 */

public interface IObservableSource<T> {

    void subscribe(IObserver<? super T> observer);

}
