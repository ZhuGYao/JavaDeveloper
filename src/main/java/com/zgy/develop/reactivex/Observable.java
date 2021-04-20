package com.zgy.develop.reactivex;

/**
 * @author zgy
 * @data 2021/4/20 10:20
 */

public class Observable<T> implements ObservableSource<T> {

    private ObservableOnSubscribe<T> observableOnSubscribe;

    private Observable(ObservableOnSubscribe<T> observableOnSubscribe) {
        this.observableOnSubscribe = observableOnSubscribe;
    }

    @Override
    public void subscribe(Observer<T> observer) {
        observableOnSubscribe.call(observer);
    }

    /**
     * 初始化,返回对象
     * @param observableOnSubscribe
     * @return
     */
    public static <T> Observable<T> create(ObservableOnSubscribe<T> observableOnSubscribe) {
        return new Observable<>(observableOnSubscribe);
    }

    public <R> Observable<R> map(Function<T, R> func) {
        return lift(new ExecuteMap<T, R>(func));
    }

    public <R> Observable<R> lift(Execute<R, T> execute) {
        return new Observable<>(new ObservableLift<T, R>(observableOnSubscribe, execute));
    }
}
