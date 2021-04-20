package com.zgy.develop.rxjava;

/**
 * @author zgy
 * @data 2021/4/20 10:20
 */

public class Observable<T> implements IObservableSource<T> {

    private IOnObserver<T> onObserver;

    private Observable(IOnObserver<T> onObserver) {
        this.onObserver = onObserver;
    }

    @Override
    public void subscribe(IObserver<? super T> observer) {
        onObserver.call(observer);
    }
}
