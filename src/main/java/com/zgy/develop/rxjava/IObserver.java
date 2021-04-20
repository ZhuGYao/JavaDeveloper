package com.zgy.develop.rxjava;

/**
 * @author zgy  Subscriber.java
 * @data 2021/4/20 10:19
 */

public interface IObserver<T> {

    void onSubscribe();

    void onNext(T value);

    void onError(Throwable e);

    void onComplete();
}
