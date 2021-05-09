package com.zgy.develop.reactivex;

/**
 * @author zgy
 * @data 2021/4/20 14:15
 */

public class Test {

    public static void main(String[] args) {

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void call(Observer<String> observer) {
                observer.onNext("1111");
            }
        }).subscribe(new Observer<String>() {

            @Override
            public void onNext(String value) {
                System.out.println(value);
            }
        });

        // 父
//        Observable<String> stringObservable = Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void call(Observer<String> stringObserver) {
//                stringObserver.onNext("1111");
//            }
//        });
//
//        // 父去调用map，传入转换方法,得到map对象
//        Observable<Integer> map = stringObservable.map(new Function<String, Integer>() {
//            @Override
//            public Integer call(String s) {
//                return Integer.parseInt(s);
//            }
//        });
//
//        // 监听者监听map对象
//        map.subscribe(new Observer<Integer>() {
//            @Override
//            public void onNext(Integer value) {
//                System.out.println(value + 22);
//            }
//        });
//
//        map.subscribe(new Observer<Integer>() {
//            @Override
//            public void onNext(Integer value) {
//                System.out.println(value + 33);
//            }
//        });

        Observable.just(4, 5, 7, 8, 9).subscribe(new Observer<Integer>() {
                    @Override
                    public void onNext(Integer integer) {
                        System.out.println(integer);
                    }
                });

    }
}
