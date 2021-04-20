package com.zgy.develop.reactivex;

/**
 * @author zgy
 * @data 2021/4/20 15:00
 */

public class ObservableLift<T, R> implements ObservableOnSubscribe<R>{

    private ObservableOnSubscribe<T> parent;
    private Execute<R, T> execute;

    public ObservableLift(ObservableOnSubscribe<T> parent, Execute<R, T> execute) {
        this.parent = parent;
        this.execute = execute;
    }

    @Override
    public void call(Observer<R> rObserver) {
        // map内部对象
        Observer<T> st = execute.call(rObserver);
        // 执行内部对象的next方法
        parent.call(st);
    }
}
