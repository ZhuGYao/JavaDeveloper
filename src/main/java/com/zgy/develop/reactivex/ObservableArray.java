package com.zgy.develop.reactivex;

/**
 * @author zgy
 * @data 2021/4/20 16:41
 */

public class ObservableArray<T> implements ObservableOnSubscribe<T> {

    private T[] array;

    public ObservableArray(T[] array) {
        this.array = array;
    }

    @Override
    public void call(Observer<T> tObserver) {
        for (T t : array) {
            tObserver.onNext(t);
        }
    }
}
