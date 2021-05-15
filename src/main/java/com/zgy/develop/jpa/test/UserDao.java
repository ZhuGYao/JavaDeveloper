package com.zgy.develop.jpa.test;

import com.zgy.develop.jpa.base.MySQLBaseDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zgy
 * @data 2021/4/16 17:22
 */

public class UserDao extends MySQLBaseDao<User> {

    public int add(User user) {
        return this.insert(user);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // FutureTask实现了Runnable，可以看做是一个任务
        FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println(Thread.currentThread().getName() + "========>正在执行");
                try {
                    Thread.sleep(3 * 1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "success";
            }
        });

        System.out.println(Thread.currentThread().getName() + "========>启动任务");

        // 传入futureTask，启动线程执行任务
        new Thread(futureTask).start();

        futureTask.isDone();
        // 但它同时又实现了Future，可以获取异步结果（会阻塞3秒）
//        刚创建
//        即将完成
//        完成
//        抛异常
//        任务取消
//        任务即将被打断
//        任务被打断
        String result = futureTask.get();
        System.out.println("任务执行结束，result====>" + result);



//
//        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition condition = reentrantLock.newCondition();
        condition.await();
        condition.signal();
//        LongAdder longAdder = new LongAdder();
//        longAdder.increment();
//
//        List<String> list = new ArrayList<>();
//        list.add("111");
//        Long l = Long.valueOf(list.size());
//        System.out.println(l);
    }

}
