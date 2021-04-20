package com.zgy.develop.reactivex;

/**
 * @author zgy
 * @data 2021/4/20 14:53
 */

public class ExecuteMap <T, R> implements Execute<R, T>{

    private Function<T, R> transformer;

    public ExecuteMap(Function<T, R> transformer) {
        this.transformer = transformer;
    }

    @Override
    public Observer<T> call(Observer<R> rObserver) {
        return new MapObserver<>(rObserver, transformer);
    }

    private class MapObserver<T, R> implements Observer<T> {

        private Observer<R> observer;
        private Function<T, R> transformer;

        public MapObserver(Observer<R> observer, Function<T, R> transformer) {
            this.observer = observer;
            this.transformer = transformer;
        }

        @Override
        public void onNext(T t) {
            // 获取数据,进行转换
            R r = transformer.call(t);
            // 下发监听者
            observer.onNext(r);
        }
    }
}
