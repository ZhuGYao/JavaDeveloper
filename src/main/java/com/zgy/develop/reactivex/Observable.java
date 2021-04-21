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

    /**
     * 映射转换
     * @param func 转换函数
     * @param <R>
     * @return
     */
    public <R> Observable<R> map(Function<T, R> func) {
        return lift(new ExecuteMap<>(func));
    }

    /**
     *
     * @param execute
     * @param <R>
     * @return
     */
    public <R> Observable<R> lift(Execute<R, T> execute) {
        return create(new ObservableLift<>(observableOnSubscribe, execute));
    }

    public static <T> Observable<T> just(T ...t) {
        return from(t);
    }

    public static <T> Observable<T> from(T[] array) {
        return create(new ObservableArray<T>(array));
    }
}
